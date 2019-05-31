//package com.fulmicotone.aws.cluster.cross.api;
//
//import com.amazonaws.services.datapipeline.DataPipelineClientBuilder;
//import MyActivity;
//import MyCluster;
//import MyDefault;
//import MySnsAlarm;
//import JavaSparkStep;
//import SparkDeployMode;
//import ClusterBehaviourConf;
//import ClusterHwConf;
//import ClusterSecurityConf;
//import ClusterSwConf;
//import com.fulmicotone.aws.cluster.cross.api.models.*;
//import FailureAndRerunMode;
//import PipelineObjectsClasses;
//import MyFailureAndRerunMode;
//import MyInteger;
//import MyString;
//import MyPipelineService;
//import Java8HomePropertySupplier;
//import MyPipelineObjectFactory;
//import org.junit.Test;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//public class DefineAndActivatePipelineSample {
//
//
//
//    //uncomment test and try
//    @Test
//    public void start(){
//
//
//        MyPipelineService myPipelineService = new MyPipelineService( DataPipelineClientBuilder.defaultClient());
//
//
//        //DEFINE CLUSTER
//        EmrCluster myCluster = getEMRCluster();
//
//
//        //DEFINE STEP
//        JavaSparkStep step = new JavaSparkStep.JavaSparkBuilder().
//                withMasterUrl("yarn-cluster", SparkDeployMode.cluster)
//                .withJar("myApp.jar")
//                .withMainClass("com.ciao.MainClass")
//                .create();
//
//
//        SnsAlarm alarm = MySnsAlarm.MySnsAlarmBuilder.newOne()
//                .withTag("acaso", "SnsAlarm")
//                .withMessage(new MyString("pippo"))
//                .withRole(new MyString("EMR_DefaultRole"))
//                .withSubject(new MyString("Subject"))
//                .withTopicArn(new MyString("Topic"))
//                .creation();
//
//        //DEFINE ACTIVITY
//        EmrActivity myActivity = MyActivity.newEMR()
//                .withStep(new MyString(step.toString()))
//                .withTag("MyActivityExample", "MyActivityExample")
//                .withOnSuccess(alarm)
//                .withRunOn(myCluster).creation();
//
//
//        EmrActivity myActivity2 = MyActivity.newEMR()
//                .withStep(new MyString(step.toString()))
//                .withTag("MyActivityExample2", "MyActivityExample2")
//                .withRunOnWorkergroup(new MyString("wg-mine")).creation();
//
//
//        //DEFINE PIPELINE
//        MyPipeline pipeline = MyPipeline.newOne(provideEmptyDefault())
//                .addActivity(myActivity)
//                .addActivity(myActivity2)
//                .create();
//
//
//
//        String pipelineId = myPipelineService.createAndDefine("prova", pipeline, new HashMap<>());
//
//
//    }
//
//
//    private EmrCluster getEMRCluster(){
//
//
//
//        MyFailureAndRerunMode fm = new MyFailureAndRerunMode();
//        fm.setVal(FailureAndRerunMode.cascade);
//
//        /**software configurations**/
//        ClusterSwConf swConf = ClusterSwConf
//                .ClusterSwConfBuilder.newOne()
//                .withReleaseLabel(new MyString("emr-5.13.0"))
//                .withApplications(new MyString("spark"))
//                .addConfiguration(getEMRConf().get(0))
//                .addConfiguration(getEMRConf().get(1))
//                .build();
//
//        /**security configurations**/
//        ClusterSecurityConf secConf = ClusterSecurityConf.ClusterSecuityConfBuilder.newOne()
//                .withRole(new MyString("EtlDataPipelineRole"))
//                .withResourceRole(new MyString("dataPipelineResourceInstanceProfile"))
//                .build();
//
//        /**behaviour**/
//        ClusterBehaviourConf behaviourConf = ClusterBehaviourConf.ClusterBehaviourConfBuilder.newOne()
//                .withMaximumRetries(new MyInteger(1))
//                .withTerminateAfter(new MyString("3 Hours"))
//                .withFailureAndRerunMode(fm)
//                .build();
//
//
//        /**hardware config**/
//
//        ClusterHwConf hwConf = ClusterHwConf.ClusterHwConfBuilder
//                .newOne()
//                .withCoreInstanceType(new MyString("m1.large"))
//                .withMasterInstanceType(new MyString("m1.large"))
//                .withCoreInstanceCount(new MyInteger(2))
//                .build();
//
//        return  MyCluster.MyClusterBuilder.newOne()
//                .withClusterHwConf(hwConf)
//                .withClusterSecurityConf(secConf)
//                .withClusterBehaviourConf(behaviourConf)
//                .withClusterSwConf(swConf)
//                .withTag("MyClusterId",  "MyClusterTestName")
//                .build();
//
//
//
//
//    }
//
//
//    private List<EmrConfiguration> getEMRConf(){
//
//
//
//   EmrConfiguration sEnv= MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Configuration, "spark-defaults");
//
//      EmrConfiguration hEnv= MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Configuration, "hadoop-defaults");
//
//      EmrConfiguration exportConf=MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Configuration, "export");
//
//      exportConf.classification=new MyString("export");
//      exportConf.property.add(new Java8HomePropertySupplier().get());
//
//
//        Property pr = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Property, "c");
//        pr.key.setVal("ciao");
//        pr.value.setVal("strong");
//        exportConf.property.add(pr);
//
//
//      sEnv.classification=new MyString("spark-env");
//
//      sEnv.configuration.add(exportConf);
//
//
//
//      hEnv.classification=new MyString("hadoop-env");
//
//
//      hEnv.configuration.add(exportConf);
//
//
//
//
//      return Arrays.asList(sEnv,hEnv);
//
//
//
//  }
//
//
//
//
//
//
//    /**
//     *
//     * @return Default Object with failureRerunMode Cascade
//     */
//    protected static Default provideEmptyDefault(){
//
//        MyFailureAndRerunMode failureRerunMode = new MyFailureAndRerunMode();
//
//        failureRerunMode.setVal(FailureAndRerunMode.cascade);
//
//        //creating default pipeline object
//        Default aDefault = MyDefault.newOne()
//                .withTag("Default", "Default")
//                .withFailureAndRerunMode(failureRerunMode)
//                .creation();
//
//        return aDefault;
//    }
//
//
//}