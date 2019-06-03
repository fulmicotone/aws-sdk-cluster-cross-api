package com.fulmicotone.aws.cluster.cross.api;

import com.amazonaws.auth.policy.actions.ElasticMapReduceActions;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.datapipeline.model.Field;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClientBuilder;
import com.amazonaws.services.elasticmapreduce.model.*;
import com.fulmicotone.aws.cluster.cross.api.builder.MyActivity;
import com.fulmicotone.aws.cluster.cross.api.builder.MyCluster;
import com.fulmicotone.aws.cluster.cross.api.function.SupplierParameterizableEmrBuilderWithSparkDefault;
import com.fulmicotone.aws.cluster.cross.api.function.SupplierParameterizableEmrResourceBuilder;
import com.fulmicotone.aws.cluster.cross.api.function.SupplierParametrizableSparkDefaultsConfig;
import com.fulmicotone.aws.cluster.cross.api.function.my.FnGetUniqueResourcesFromActivities;
import com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline.FnMyPipelineObjectToAWSFields;
import com.fulmicotone.aws.cluster.cross.api.models.*;
import com.fulmicotone.aws.cluster.cross.api.models.enums.FailureAndRerunMode;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.models.enums.ScheduleType;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyDuration;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyInteger;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.JavaSparkStep;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyParams;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.SparkDeployMode;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterHwConf;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterSecurityConf;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.business.MyFindOrCreateEmrObservableResource;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.business.getter.MyGetterEMRCluster;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.business.getter.filters.MyEMRClusterFilterActiveNameContains;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.model.MyEMRScaleUpPolicy;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.model.MyEMRThresholdBreakBehaviour;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.ZoneOffset.UTC;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyTest {
    private static Default defaultPipelineExample;
    private static EmrConfiguration configurationExample;
    private static Property propertyExample;
    private static
    FnMyPipelineObjectToAWSFields fn_my_pipeline_object_to_aws_fields = new FnMyPipelineObjectToAWSFields();

    @BeforeClass
    public static void init() {

        Schedule dailySchedule = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Schedule, "MySchedule");
        defaultPipelineExample = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Default, "IdDefault");
        defaultPipelineExample.scheduleType.setVal(ScheduleType.cron);
        defaultPipelineExample.schedule = dailySchedule;
        defaultPipelineExample.failureAndRerunMode.setVal(FailureAndRerunMode.cascade);
        defaultPipelineExample.resourceRole.setPlaceHolder("myEmrResourceRole");

        dailySchedule.startDateTime
                .setVal(LocalDateTime
                        .of(2018, 06, 10, 00, 00, 10, 20)
                        .atOffset(UTC).toLocalDateTime());

        dailySchedule.period.setVal(1, ChronoUnit.DAYS);


        Property property1 = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Property, "myProp1");
        property1.key.setVal("mykey");
        property1.value.setVal("myvalue");

        Property property2 = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Property, "myProp2");
        property2.key.setVal("mykey1");
        property2.value.setVal("myvalue1");

        Property property3 = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Property, "myProp3");
        property3.key.setVal("mykey3");
        property3.value.setVal("myvalue3");


        EmrConfiguration innerConfig = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Configuration,
                "innerConfigurationWithOneProperty");
        innerConfig.classification.setVal("innerconfig");
        innerConfig.property.add(property3);

        configurationExample = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Configuration,
                "exportConfigWithTwoProperties");
        configurationExample.classification.setVal("export");
        configurationExample.property.add(property1);
        configurationExample.property.add(property2);
        configurationExample.configuration.add(innerConfig);


        propertyExample = property1;

    }


    /**
     * decode  emr configurations in data pipeline aws field
     */
    @Test
    public void testAFnPipelineObjectListField() {


        Iterator<String> r = Arrays
                .asList("{Key: period,StringValue: 1 days,}",
                        "{Key: startDateTime,StringValue: 2018-06-10T12:00:10,}",
                        "{Key: id,StringValue: MySchedule,}",
                        "{Key: name,StringValue: MySchedule,}",
                        "{Key: type,StringValue: Schedule,}"
                ).iterator();

        fn_my_pipeline_object_to_aws_fields.apply(defaultPipelineExample.schedule).stream().map(Field::toString)
                //       .peek(System.out::println)
                .forEach(pval -> Assert
                        .assertTrue(r.next()
                                .equals(pval)));


        Iterator<String> r2 = Arrays
                .asList("{Key: classification,StringValue: " + configurationExample.classification.getVal() + ",}",
                        "{Key: configuration,RefValue: " + configurationExample.configuration.get(0).id.getVal() + "}",
                        "{Key: property,RefValue: " + configurationExample.property.get(0).id.getVal() + "}",
                        "{Key: property,RefValue: " + configurationExample.property.get(1).id.getVal() + "}",
                        "{Key: id,StringValue: " + configurationExample.id.getVal() + ",}",
                        "{Key: name,StringValue: " + configurationExample.name.getVal() + ",}",
                        "{Key: type,StringValue: " + configurationExample.getClass().getSimpleName() + ",}"

                ).iterator();


        fn_my_pipeline_object_to_aws_fields.apply(this.configurationExample)
                .stream().map(Field::toString)
                .peek(System.out::println)
                .forEach(awsField -> Assert
                        .assertTrue(awsField.equals(r2.next())));


    }


    /**
     * Check My Duration Object
     */
    @Test
    public void testBMyPipelineObjectPeriodField() {


        MyDuration p1 = new MyDuration();
        p1.setVal(1, ChronoUnit.MONTHS);

        MyDuration p2 = new MyDuration();
        p2.setVal(1, ChronoUnit.YEARS);

        MyDuration p3 = new MyDuration();
        p3.setVal(10, ChronoUnit.HOURS);

        MyDuration p4 = new MyDuration();
        p4.setVal(4, ChronoUnit.WEEKS);

        List<String> expectedResult = Arrays.asList("1 months", "1 years", "10 hours");


        AtomicInteger i = new AtomicInteger(-1);

        Arrays.asList(p1, p2, p3).stream().map(MyDuration::getVal)
                .peek(x->i.getAndIncrement())
                .forEach(pval ->
                        Assert.assertTrue("expected"+expectedResult.get(i.get())+"found:"+pval,
                                expectedResult.get(i.get()).equals(pval)));
    }


    @Test
    public void testCGetUniqueResource() {

        EmrCluster cluster = MyCluster.MyClusterBuilder.newOne()

                .withClusterHwConf(ClusterHwConf.ClusterHwConfBuilder
                        .newOne()
                        .withCoreInstanceType(new MyString("xx"))
                        .withMasterInstanceType(new MyString("xxxx"))
                        .withCoreInstanceCount(new MyInteger(1))
                        .build())
                .withClusterSecurityConf(ClusterSecurityConf.ClusterSecuityConfBuilder.newOne()
                        .withKeyPair(new MyString("yy"))
                        .withSubnetId(new MyString("yy"))
                        .withRole(new MyString("yy"))
                        .withResourceRole(new MyString("yy")).build())

                .withTag("id", "id")
                .build();

        Assert.assertTrue(new FnGetUniqueResourcesFromActivities().apply(
                Arrays.asList(MyActivity.newEMR()
                                .withStep(new MyString("fdfdf"))
                                .withTag("translateActivity",
                                        "translateActivity")
                                .withRunOn(cluster).creation(),
                        MyActivity.newEMR()
                                .withStep(new MyString("fdfdf"))
                                .withTag("x", "x")
                                .withRunOn(cluster).creation(),
                        MyActivity.newEMR()
                                .withStep(new MyString("fhhhhhh"))
                                .withTag("y", "y")
                                .withRunOnWorkergroup(new MyString("wg-mine")).creation(),

                        MyActivity.newEMR()
                                .withStep(new MyString("fdfdf"))
                                .withTag("t", "t")
                                .withRunOn(cluster).creation())).size() == 1);
    }

    @Test
    public void testDEmrParametrizableBuilder() {


        MyCluster.MyClusterBuilder emrResourceBuilder = new SupplierParameterizableEmrResourceBuilder().get();

        EmrConfiguration sparkDefault = new SupplierParametrizableSparkDefaultsConfig().get();

        Iterator<String> expectedResultEmrResourceBuilder1 = Arrays.asList(
                "{Key: subnetId,StringValue: #{myEmrSubnet},}",
                "{Key: masterInstanceType,StringValue: #{myEmrMasterType},}",
                "{Key: coreInstanceType,StringValue: #{myEmrCoreType},}",
                "{Key: masterInstanceBidPrice,StringValue: #{myEmrMasterBid},}",
                "{Key: coreInstanceCount,StringValue: #{myEmrCoreCount},}",
                "{Key: region,StringValue: #{myEmrRegion},}",
                "{Key: terminateAfter,StringValue: #{myTerminateAfter},}",
                "{Key: applications,StringValue: #{myEmrApplications},}",
                "{Key: keyPair,StringValue: #{myEmrKeyPair},}",
                "{Key: coreInstanceBidPrice,StringValue: #{myEmrCoreBid},}",
                "{Key: maximumRetries,StringValue: #{myMaxRetries},}",
                "{Key: releaseLabel,StringValue: #{myEmrReleaseLabel},}",
                "{Key: resourceRole,StringValue: #{myEmrResourceRole},}",
                "{Key: role,StringValue: #{myEmrRole},}",
                "{Key: pipelineLogUri,StringValue: #{myEmrLogPipelineURI},}",
                "{Key: failureAndRerunMode,StringValue: #{myFailureAndRerunMode},}",
                "{Key: id,StringValue: x,}",
                "{Key: name,StringValue: Y,}",
                "{Key: type,StringValue: EmrCluster,}").iterator();

        Iterator<String> expectedResultSparkDefault2 = Arrays.asList(
                "{Key: key,StringValue: spark.executor.instances,}",
                "{Key: value,StringValue: #{myEmrConfigExecutorInstances},}",
                "{Key: id,StringValue: spark.executor.instances,}",
                "{Key: name,StringValue: spark.executor.instances,}",
                "{Key: type,StringValue: Property,}",
                "{Key: key,StringValue: spark.executor.memoryOverhead,}",
                "{Key: value,StringValue: #{myEmrConfigExecutorOverhead},}",
                "{Key: id,StringValue: spark.executor.memoryOverhead,}",
                "{Key: name,StringValue: spark.executor.memoryOverhead,}",
                "{Key: type,StringValue: Property,}",
                "{Key: key,StringValue: spark.driver.memoryOverhead,}",
                "{Key: value,StringValue: #{myEmrConfigDriverOverhead},}",
                "{Key: id,StringValue: spark.driver.memoryOverhead,}",
                "{Key: name,StringValue: spark.driver.memoryOverhead,}",
                "{Key: type,StringValue: Property,}",
                "{Key: key,StringValue: spark.executor.memory,}",
                "{Key: value,StringValue: #{myEmrConfigExecutorMemory},}",
                "{Key: id,StringValue: spark.executor.memory,}",
                "{Key: name,StringValue: spark.executor.memory,}",
                "{Key: type,StringValue: Property,}",
                "{Key: key,StringValue: spark.driver.memory,}",
                "{Key: value,StringValue: #{myEmrConfigDriverMemory},}",
                "{Key: id,StringValue: spark.driver.memory,}",
                "{Key: name,StringValue: spark.driver.memory,}",
                "{Key: type,StringValue: Property,}",
                "{Key: key,StringValue: spark.executor.cores,}",
                "{Key: value,StringValue: #{myEmrConfigExecutorCore},}",
                "{Key: id,StringValue: spark.executor.cores,}",
                "{Key: name,StringValue: spark.executor.cores,}",
                "{Key: type,StringValue: Property,}",
                "{Key: key,StringValue: spark.driver.cores,}",
                "{Key: value,StringValue: #{myEmrConfigDriverCore},}",
                "{Key: id,StringValue: spark.driver.cores,}",
                "{Key: name,StringValue: spark.driver.cores,}",
                "{Key: type,StringValue: Property,}",
                "{Key: key,StringValue: spark.default.parallelism,}",
                "{Key: value,StringValue: #{myEmrConfigDefaultParallelism},}",
                "{Key: id,StringValue: spark.default.parallelism,}",
                "{Key: name,StringValue: spark.default.parallelism,}",
                "{Key: type,StringValue: Property,}").iterator();


        Iterator<String> expectedResult3 = Arrays.asList(
                "myEmrMasterBid=20.0",
                "myEmrMasterType=m4",
                "myEmrReleaseLabel=release",
                "myEmrConfigDynamicAllocationMaxExecutors=30",
                "myEmrSubnet=mysubnet",
                "myEmrCoreCount=3",
                "myEmrResourceRole=myinstancerolehere",
                "myEmrCoreBid=20.0",
                "myEmrCoreType=m4",
                "myEmrConfigExecutorOverhead=34",
                "myEmrConfigExecutorCore=4",
                "myEmrKeyPair=chiave",
                "myEmrApplications=spark",
                "myEmrRole=myservicerolehere",
                "myEmrConfigExecutorMemory=4G").iterator();


        fn_my_pipeline_object_to_aws_fields.apply(emrResourceBuilder.withTag("x", "Y").build())
                .stream().map(Field::toString)
                .forEach(value -> Assert.assertTrue(value.equals(expectedResultEmrResourceBuilder1.next())));

        sparkDefault.property.stream().flatMap(p -> fn_my_pipeline_object_to_aws_fields.apply(p).stream())
                .map(Field::toString)
                .forEach(value ->
                        {
                                String ev = expectedResultSparkDefault2.next();
                                Assert.assertTrue("unexpected value :"+value+" expected:"+ev,value.equals(ev));
                        }
                );


        MyParams p = new MyParams();

        p.emrResource().withApplications("spark")
                .withCoreBid(20.00f)
                .withCoreCount(3)
                .withCoreType("m4")
                .withConfigDynamicAllocatioNMaxExecutors(30)
                .withConfigExecutorCore(4)
                .withConfigExecutorMemory(4)
                .withKeyPair("chiave")
                .withMasterBid(20f)
                .withMasterType("m4")
                .withReleaseLabel("release")
                .withResourceRole("myinstancerolehere")
                .withRole("myservicerolehere")
                .withSubnet("mysubnet")
                .withConfigExecutorOverhead(34);


        p.entrySet().stream().map(Map.Entry::toString)

                .forEach(val ->{
                    String ev = expectedResult3.next();
                   Assert.assertTrue("unExpectedValue:" + val +" instead of :"+ev, val.equals(ev));
                    }
                        );
    }



    @Test
    public void javaSparkStepBuilder(){

        JavaSparkStep stepExample = new JavaSparkStep.JavaSparkBuilder()
                .withMasterUrl("yarn-cluster", SparkDeployMode.cluster)
                .withJar("s3://myApp.jar")
                .withMainClass("mia.app.Main")
                .addArgs("-cmd",String.join(".","mia","app","faicose"))
                .addArgs("-startDate","2019")
                .addConfigs("spark.sql.parquet.fs.optimized.committer.optimization-enabled", "true")
                .create();


        StepConfig f = stepExample.toEmrStepConfig("ciao", ActionOnFailure.CONTINUE);

        String stringStep = stepExample.toString();

        String expected="command-runner.jar,spark-submit,--master,yarn-cluster,--deploy-mode,cluster," +
                "--conf,spark.sql.parquet.fs.optimized.committer.optimization-enabled=true," +
                "--class,mia.app.Main,s3://myApp.jar,-cmd,mia.app.faicose,-startDate,2019";

        Assert.assertTrue(expected.equals(stringStep));

    }







}