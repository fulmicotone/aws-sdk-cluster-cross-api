package com.fulmicotone.aws.cluster.cross.api.spec.emr.business;

import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.model.ClusterSummary;
import com.amazonaws.services.elasticmapreduce.model.InternalServerException;
import com.evanlennick.retry4j.CallExecutorBuilder;
import com.evanlennick.retry4j.config.RetryConfigBuilder;
import com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.emr.FnGetEmrCluster;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.MyEmrFilterListRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.MILLIS;

/** EMR FINDER WITH M OUT OF N **/
public class MyEMRFinder {

    Logger log= LoggerFactory.getLogger(MyEMRFinder.class);

    private final AmazonElasticMapReduce emr;
    private final int nRetry;
    private final int delay;

    private MyEmrFilterListRequest filterRequest;


    private MyEMRFinder(EMRFinderBuilder b){

        this.filterRequest=b.filterRequest;
       this.nRetry=b.nRetry;
       this.delay=b.delayInMillis;
        this.emr=b.emr;

    }



    public List<ClusterSummary> search(){

        FnGetEmrCluster fn = new FnGetEmrCluster();

        RetryConfigBuilder conf = new RetryConfigBuilder()
                .retryOnSpecificExceptions(InternalServerException.class)
                .withMaxNumberOfTries(nRetry)
                .withDelayBetweenTries(delay, MILLIS)
                .withRandomExponentialBackoff();


        log.info("EMR Cluster Finder start with exponential random delay max attempt:{}",nRetry);

        return

                (List<ClusterSummary>) new CallExecutorBuilder<>()
                        .config(conf.build())
                        .onSuccessListener(s ->  log.info("EMR Cluster Finder Success!"))
                        .onFailureListener( s -> log.info("EMR Cluster Finder Failed! All retries exhausted..."))
                        .afterFailedTryListener(s -> log.info("Try failed! Will try again in "+delay+"ms."))
                        .build()
                        .execute( () -> fn.apply(emr,filterRequest))
                        .getResult();



    }


    public static final class EMRFinderBuilder {

        private final AmazonElasticMapReduce emr;
        private MyEmrFilterListRequest filterRequest;

        private int nRetry;
        private int delayInMillis;


        private EMRFinderBuilder(AmazonElasticMapReduce emr) { this.emr=emr; }

        public static EMRFinderBuilder newOne(AmazonElasticMapReduce emr) { return new EMRFinderBuilder(emr); }

     public EMRFinderBuilder withMaxRetry(int n,int delayInMillis){

           this.nRetry=n;
           this.delayInMillis=delayInMillis;
            return this;
     }


        public EMRFinderBuilder withFilterRequest(MyEmrFilterListRequest filterRequest) {
            this.filterRequest = filterRequest;
            return this;
        }

        public MyEMRFinder build() {
            Objects.requireNonNull(filterRequest,"filter request is required!!!");
            return new MyEMRFinder(this);
        }
    }
}
