## AWS-SDK-Cross-Api 

This SDK try to simplify and get a standard way to traits cluster between emr and data pipeline. 
Is easy to create a pipeline  programmatically  using  the  Objects provided. 

[![](https://jitpack.io/v/fulmicotone/aws-sdk-cluster-cross-api.svg)](https://jitpack.io/#fulmicotone/aws-sdk-cluster-cross-api)

## Models to compose

- AWSAction
- AWSActivity
- AWSConfiguration
- AWSDatabase
- AWSDataFormat
- AWSDatanode
- AWSPrecondiction
- AWSProperty
- AWSResource
- AWSSChedule


# EMR Cluster Sample 


#### how to create en EMR Cluster for data pipeline service in the traditional way
        String name = "EmrClusterForBackup";
        String id = "EmrClusterForBackup";

        Field type = new Field().withKey("type").withStringValue("EmrCluster");
        Field amiVersion = new Field().withKey("amiVersion").withStringValue("3.10.0");
        Field masterInstanceType = new Field().withKey("masterInstanceType").withStringValue("m3.xlarge");
        Field coreInstanceType = new Field().withKey("coreInstanceType").withStringValue("m3.xlarge");
        Field coreInstanceCount = new Field().withKey("coreInstanceCount").withStringValue("1");
        Field region = new Field().withKey("region").withStringValue("#{myDDBRegion}");
        Field terminateAfter = new Field().withKey("terminateAfter").withStringValue("12 hours");
        Field bootstrapAction = new Field().withKey("bootstrapAction").withStringValue("s3://elasticmapreduce" +
                "/bootstrap-actions/configure-hadoop, --yarn-key-value,yarn.nodemanager.resource.memory-mb=11520," +
                "--yarn-key-value,yarn.scheduler.maximum-allocation-mb=11520," +
                "--yarn-key-value,yarn.scheduler.minimum-allocation-mb=1440," +
                "--yarn-key-value,yarn.app.mapreduce.am.resource.mb=2880," +
                "--mapred-key-value,mapreduce.map.memory.mb=5760," +
                "--mapred-key-value,mapreduce.map.java.opts=-Xmx4608M," +
                "--mapred-key-value,mapreduce.reduce.memory.mb=2880," +
                "--mapred-key-value,mapreduce.reduce.java.opts=-Xmx2304m," +
                "--mapred-key-value,mapreduce.map.speculative=false");

        List<Field> fieldsList = Lists.newArrayList(type, amiVersion, masterInstanceType,
                coreInstanceCount, coreInstanceType, region, terminateAfter, bootstrapAction);

        return new PipelineObject().withName(name).withId(id).withFields(fieldsList);

#### using this SDK

        MyCluster.MyClusterBuilder.newOne()
                .withClusterHwConf( ClusterHwConfBuilder
                                                   .newOne()
                                                   .withCoreInstanceType(new MyString("m3.xlarge"))
                                                   .withMasterInstanceType(new MyString("m3.xlarge"))
                                                   .withCoreInstanceCount(new MyInteger(2))
                                                   .build())
                .withClusterSecurityConf(secConf)
                .withClusterBehaviourConf( ClusterBehaviourConfBuilder.newOne()
                                                          .withTerminateAfter(new MyString("12 Hours"))
                                                          .withBootstrapAction("ecc")
                                                          .build();)
                .withClusterSwConf(swConf)
                .build("EmrClusterForBackup", "EmrClusterForBackup");




## Pipeline definition example


        MyPipelineService myPipelineService = new MyPipelineService( DataPipelineClientBuilder.defaultClient());


        //DEFINE CLUSTER
        EmrCluster myCluster = getEMRCluster(); //as we described above


        //DEFINE STEP
        JavaSparkStep step = new JavaSparkStep.JavaSparkBuilder().
                withMasterUrl("yarn-cluster", SparkDeployMode.cluster)
                .withJar("myApp.jar")
                .withMainClass("com.ciao.MainClass")
                .create();


        //DEFINE ACTIVITY
        EmrActivity myActivity = MyActivity.newEMR()
                .withStep(new MyString(step.toString()))
                .withTag("MyActivityExample", "MyActivityExample")
                .withRunOn(myCluster).creation();


        //DEFINE PIPELINE
        MyPipeline pipeline = MyPipeline.newOne(provideEmptyDefault())
                .addActivity(myActivity)
                .create();



        String pipelineId = myPipelineService.createAndDefine("prova", pipeline, new HashMap<>())




###### Todo

- emr cluster builder adding control on not null fields (role is required)

- myPipelineService.createAndDefine(s3Key, myPipeline, null); required not null on parameters

- when you define Default object the id must be Default 

