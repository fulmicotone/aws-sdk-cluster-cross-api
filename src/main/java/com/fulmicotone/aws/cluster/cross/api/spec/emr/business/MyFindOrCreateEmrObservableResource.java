package com.fulmicotone.aws.cluster.cross.api.spec.emr.business;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmResult;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClientBuilder;
import com.amazonaws.services.elasticmapreduce.model.*;
import com.evanlennick.retry4j.CallExecutorBuilder;
import com.evanlennick.retry4j.config.RetryConfigBuilder;
import com.fulmicotone.aws.cluster.cross.api.builder.MyCluster;
import com.fulmicotone.aws.cluster.cross.api.exception.MyEMRIsIdleAlarmCreationException;
import com.fulmicotone.aws.cluster.cross.api.exception.MyEMRResourceCreationException;
import com.fulmicotone.aws.cluster.cross.api.exception.MyResourceGetException;
import com.fulmicotone.aws.cluster.cross.api.function.SupplierParameterizableEmrBuilderWithSparkDefault;
import com.fulmicotone.aws.cluster.cross.api.function.SupplierParameterizableEmrResourceBuilder;
import com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.emr.FnMyEmrBuilderToRunJobFlowRequest;
import com.fulmicotone.aws.cluster.cross.api.spec.cloudwatch.business.MyEMRIsIndleAlarm;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyParams;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.TaskRunnerStep;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.business.getter.MyGetterEMRCluster;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.business.getter.filters.MyEMRClusterFilterActiveNameContains;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.business.getter.filters.generic.MyEMRStepsListRequest;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.function.FnGetEmrSteps;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.model.MyClusterSummaryDeep;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.model.MyEMRScaleUpPolicy;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.model.MyEMRThresholdBreakBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.MILLIS;

/**
 * This Class is used  to find/create an EMR Resource
 * through its Worker Group.
 * <p>
 * If the resource already exists will be linked to an alarm
 * that will monitor is Idle metric  and if will be triggered will
 * fire a message on the sns topics passed as parameters.
 */
public class MyFindOrCreateEmrObservableResource {



    Logger log = LoggerFactory.getLogger(MyFindOrCreateEmrObservableResource.class);

    private final AmazonElasticMapReduce emrClient;

    private final AmazonCloudWatch cwClient;

    private final String resourceName;

    private final MyParams params;

    private final String workerGroup;

    private final Regions region;

    private final String[] snsTopicArn;

    private final int indleLimitInSeconds;

    private final MyCluster.MyClusterBuilder emrClusterBuilder;

    private final boolean installTaskRunner;

    private final Tag[] tags;

    private final int nRetry;

    private final int millisDelay;

    private final RetryConfigBuilder retryConf;

    private final MyEMRScaleUpPolicy scalePolicy;



    public MyFindOrCreateEmrObservableResource(AmazonElasticMapReduce emrClient,
                                               AmazonCloudWatch cloudWatchClient,
                                               MyCluster.MyClusterBuilder clusterBuilder,
                                               String resourceName,
                                               String workerGroup,
                                               Regions region,
                                               int indleLimitInSeconds,
                                               boolean installTaskRunner,
                                               int nRetry,
                                               int millisDelay,
                                               MyEMRScaleUpPolicy policy,
                                               Tag[] tags,
                                               MyParams params,
                                               String... snsTopicArn) {
        this.emrClient = emrClient == null ? AmazonElasticMapReduceClientBuilder.defaultClient() : emrClient;
        this.cwClient = cloudWatchClient == null ? AmazonCloudWatchClientBuilder.defaultClient() : cloudWatchClient;
        this.resourceName = resourceName;
        this.params = params;
        this.workerGroup = workerGroup;
        this.region = region;
        this.snsTopicArn = snsTopicArn;
        this.indleLimitInSeconds=indleLimitInSeconds;
        this.emrClusterBuilder=clusterBuilder;
        this.installTaskRunner=installTaskRunner;
        this.tags=tags;
        this.nRetry=nRetry;
        this.millisDelay=millisDelay;
        this. retryConf = new RetryConfigBuilder()
                .retryOnSpecificExceptions(InternalServerException.class)
                .withMaxNumberOfTries(nRetry)
                .withDelayBetweenTries(millisDelay, MILLIS)
                .withRandomExponentialBackoff();
        this.scalePolicy=policy;

    }



    private String createNewEmrCluster(String name){

        String jobFlowId;

        //CREATION BLOCK
        try {
            //create if not exist
                //create cluster
                RunJobFlowRequest job = new FnMyEmrBuilderToRunJobFlowRequest()
                        .apply(emrClusterBuilder.withTag(name), params);
                //add tags
                Optional.ofNullable(tags).ifPresent(t -> job.setTags(new ArrayList(Arrays.asList(t))));

                job.getInstances().setKeepJobFlowAliveWhenNoSteps(true);

                Optional.ofNullable(installTaskRunner).filter(f->f)
                        .ifPresent((tr) ->
                                job.setSteps(Arrays
                                        .asList(TaskRunnerStep.TaskRunnerDefault
                                                .defaults(workerGroup, region).build())));
                //visible to all users
                job.setVisibleToAllUsers(true);


             jobFlowId = (String) new CallExecutorBuilder<>()
                    .config(retryConf.build())
                    .onSuccessListener(s -> log.info("EMR Job Creation Success!"))
                    .onFailureListener(s -> log.info("EMR Job Failed! All retries exhausted..."))
                    .afterFailedTryListener(s -> log.info("Try failed! Will try again in " + millisDelay + "ms."))
                    .build()
                    .execute(() -> {
                        try {
                            return emrClient.runJobFlow(job).getJobFlowId();
                        }catch (Exception e){
                            log.error(e.toString());
                            return null;
                        }
                    }).getResult();

            if (!Optional.ofNullable(jobFlowId).isPresent()) { throw  new Exception("creating cluster failed"); }

        }catch (Exception e){ throw  new MyEMRResourceCreationException(e.toString()); }



            try {

                final String ji = jobFlowId;

                PutMetricAlarmResult alarmResponse =

                        (PutMetricAlarmResult) new CallExecutorBuilder<>()
                                .config(retryConf.build())
                                .onSuccessListener(s -> log.info("EMR is Idle Alarm Creation Success!"))
                                .onFailureListener(s -> log.info("EMR is Idle Alarm All retries exhausted..."))
                                .afterFailedTryListener(s -> log.info("Try failed! Will try again in " + millisDelay + "ms."))
                                .build()
                                .execute(() -> cwClient.putMetricAlarm(new MyEMRIsIndleAlarm(ji,
                                        indleLimitInSeconds,
                                        2,
                                        2,
                                        snsTopicArn))).getResult();

            } catch (Exception e) { throw new MyEMRIsIdleAlarmCreationException(e.toString()); }



       return jobFlowId;

    }



    public List<MyClusterSummaryDeep> getEnrichedClusterList(List<ClusterSummary> clusterSummaryList){

        List<MyClusterSummaryDeep> re=new ArrayList<>();
        clusterSummaryList.stream()
                .map(MyEMRStepsListRequest::new)
                .map(req->(MyEMRStepsListRequest)req.withStepStates(StepState.PENDING))
                .forEach(req -> new CallExecutorBuilder<>()
                        .config(retryConf.build())
                        .onSuccessListener(s -> log.info("EMR Step List Success retrieved!"))
                        .onFailureListener(s -> log.info("EMR Step List  All retries exhausted..."))
                        .afterFailedTryListener(s -> log.info("Try failed! Will try again in " + millisDelay + "ms."))
                        .build()
                        .execute(() -> {
                            ArrayList<StepSummary> r = new ArrayList<>();
                            new FnGetEmrSteps().apply(emrClient)
                                    .apply(req).apply(stepSummaries -> r.addAll(stepSummaries));
                            re.add( new MyClusterSummaryDeep(req.getCw().get(),r));
                            return null;
                        }).getResult());
        return re;
    }


    public Optional<String> run() {

        String jobFlowId = null;
        //FINDER BLOCK
        try {

            List<ClusterSummary> activeClusters;

            String namePlusWG = String.join("_", resourceName, workerGroup);

            try {
                log.info("find cluster active and contains in name: {}",namePlusWG);
                log.info("scale policy: {}",scalePolicy.toString());


                //find cluster
                activeClusters = MyGetterEMRCluster.EMRFinderBuilder.newOne(emrClient)
                        .withMaxRetry(nRetry, millisDelay)
                        .withFilterRequest(new MyEMRClusterFilterActiveNameContains(workerGroup))
                        .build()
                        .search();
            }catch (Exception e){ throw  new MyResourceGetException(e.toString()); }
                //no clusters exist
                if (activeClusters.size()==0) {
                    log.info("no cluster found. Create observable resource with alarm it!",namePlusWG);
                    jobFlowId =createNewEmrCluster(namePlusWG+"_"+String.format("%02d", activeClusters.size()));
                }else{
                    //there's clusters
                    //enrich the cluster
                    Optional<MyClusterSummaryDeep> lessBusyCluster = getEnrichedClusterList(activeClusters)
                            .stream()//take less busy cluster //totest
                            .min((o1, o2) -> o1.getStepSummaryList().size() < o2.getStepSummaryList().size() ? 0 : 1);
                    //if the less busy cluster is up the treadshold
                    if(scalePolicy.pendingStepsBreakThreshold(lessBusyCluster.get().getStepSummaryList().size())){

                        log.info("less busy cluster {} break the threshold need to scale up!",lessBusyCluster
                                .get().getClusterSummary().getId());

                        //if can scale up
                        if(scalePolicy.clusterOpenedBreakThreshold(activeClusters.size())==false){
                            //scale up
                            log.info("scale up committing new cluster");
                            jobFlowId=createNewEmrCluster(namePlusWG+"_"+String.format("%02d", activeClusters.size()));
                            //commit in the cluster less busy
                        }else if(scalePolicy.getBehaviourOnClusterThreadsholdBreak()== MyEMRThresholdBreakBehaviour.commit_anyway)
                        {
                            log.info("we're reach the threshold limit cluster:{}. Can't scale up anymore",
                                    activeClusters.size());
                            jobFlowId=lessBusyCluster.get().getClusterSummary().getId();
                        }else{
                            log.info("we're reach the threshold limit cluster:{}. Can't scale up anymore step discarded");
                        }
                    }else{

                        log.info("return less busy cluster that satisfy scale up policy.");

                        jobFlowId=lessBusyCluster.get().getClusterSummary().getId();

                    }
                }
        }catch (MyEMRIsIdleAlarmCreationException| MyEMRResourceCreationException|MyResourceGetException e){

            if(Optional.ofNullable(jobFlowId).isPresent()) {

                final String ji=jobFlowId;

                 new CallExecutorBuilder<>()
                        .config(retryConf.build())
                        .onSuccessListener(s -> log.info("EMR Termination Success!"))
                        .onFailureListener(s -> log.info("EMR Termination All retries exhausted..."))
                        .afterFailedTryListener(s -> log.info("Try failed! Will try again in " + millisDelay + "ms."))
                        .build()
                        .execute(() -> emrClient
                                .terminateJobFlows(new TerminateJobFlowsRequest()
                                        .withJobFlowIds(ji))).getResult();
            }

            log.error("error:"+e.toString());


        }

        return Optional.ofNullable(jobFlowId);




    }

    /**
     * basic builder
     * @param <T>
     */
    protected static  class BasicBuilder <T extends BasicBuilder> {
        protected AmazonElasticMapReduce emrClient;
        protected AmazonCloudWatch cloudWatchClient;
        protected String resourceName;
        protected MyParams params;
        protected String workerGroup;
        protected Regions region = Regions.DEFAULT_REGION;
        protected List<String> snsTopicArn = new ArrayList<>();
        protected Supplier<MyCluster.MyClusterBuilder> emrClusterBuilder;
        protected Integer allowedIndlePeriod=900;
        protected  boolean installTaskRunner=true;
        protected Tag[] tags;
        private int nMaxRetries;
        private int delayInMillis;
        private int nMaxPendingSteps;
        private int maxCluster;
        private boolean wasteExceeses;
        private MyEMRScaleUpPolicy scalePolicy;


        public T withResourceName(String resourceName) {
            this.resourceName = resourceName;
            return (T) this;
        }

        public T withParams(MyParams params) {
            this.params = params;
            return (T) this;
        }

        public T withWorkerGroup(String workerGroup) {
            this.workerGroup = workerGroup;
            return (T) this;
        }


        public T addSnsTopicArn(String snsTopicArn) {
            this.snsTopicArn .add(snsTopicArn);
            return (T) this;
        }

        public T withRegion(Regions region) {
            this.region = region;
            return (T) this;
        }


        public T withTaskRunner(boolean install) {
            this.installTaskRunner = install;
            return (T) this;
        }


        public T withTags(Tag ... tags){

            this.tags=tags;
            return (T) this;
        }

        public T withScalePolicy(MyEMRScaleUpPolicy policy){

            this.scalePolicy=policy;
            return (T) this;
        }


        public T withRetryStrategy(int maxRetries,int delayInMillis){

           this.nMaxRetries =maxRetries;
           this.delayInMillis=delayInMillis;
            return (T) this;
        }


        public MyFindOrCreateEmrObservableResource build() {
            return
                    new MyFindOrCreateEmrObservableResource(
                            emrClient,
                            cloudWatchClient,
                            emrClusterBuilder.get(),
                            resourceName,
                            workerGroup,
                            region,
                            allowedIndlePeriod,
                            installTaskRunner ,
                            nMaxRetries,
                            delayInMillis,
                            scalePolicy,
                            tags,
                            params,
                            snsTopicArn.toArray(new String[]{}));
        }

    }

    /**
     * More specialized builder
     * add on its instantiation
     * emrClient,CloudWatch and a Parametrics EmrClusterBuilder with out
     * configuration place holder
     */
    public static final class BuilderStandardParametrizable
            extends BasicBuilder<BuilderStandardParametrizable> {

        private BuilderStandardParametrizable() {
            super();
            emrClient=AmazonElasticMapReduceClientBuilder.defaultClient();
            cloudWatchClient=AmazonCloudWatchClientBuilder.defaultClient();
            emrClusterBuilder=(new SupplierParameterizableEmrResourceBuilder());
        }

        public static BuilderStandardParametrizable newOne() {  return new BuilderStandardParametrizable(); }


    }

    /**
     * More specialized builder
     * add on its instantiation
     * emrClient,CloudWatch and a parametric EmrClusterBuilder plus
     * configuration place holder
     */
    public static final class BuilderStandardParametrizableWithSparkDefaultConfig
            extends BasicBuilder<BuilderStandardParametrizableWithSparkDefaultConfig> {

        private BuilderStandardParametrizableWithSparkDefaultConfig() {

            super();
            emrClient=AmazonElasticMapReduceClientBuilder.defaultClient();
            cloudWatchClient=AmazonCloudWatchClientBuilder.defaultClient();
            emrClusterBuilder=(new SupplierParameterizableEmrBuilderWithSparkDefault());
        }

        public static BuilderStandardParametrizableWithSparkDefaultConfig newOne() {
            return new BuilderStandardParametrizableWithSparkDefaultConfig();
        }
    }


        public static final class BuilderFull extends BasicBuilder<BuilderFull> {


        private BuilderFull() { }

        public static BuilderFull newOne() {
            return new BuilderFull();
        }

        public BuilderFull withEmrClient(AmazonElasticMapReduce emrClient) {
            this.emrClient = emrClient;
            return this;
        }

        public BuilderFull withCloudWatchClient(AmazonCloudWatch cloudWatchClient) {
            this.cloudWatchClient = cloudWatchClient;
            return this;
        }


        public BuilderFull wuthEmrClusterBuilderSupplier(Supplier<MyCluster.MyClusterBuilder> emrBuilder ){
            this.emrClusterBuilder=emrBuilder;
            return this;
        }


        public BuilderFull emrClusterBuilder(MyCluster.MyClusterBuilder emrBuilder ){
            this.emrClusterBuilder=()-> emrBuilder;
            return this;
        }

            public BuilderFull withIndleLimit(Integer keepAliveInSeconds) {
                this.allowedIndlePeriod=keepAliveInSeconds;
                return  this;
            }

    }
}