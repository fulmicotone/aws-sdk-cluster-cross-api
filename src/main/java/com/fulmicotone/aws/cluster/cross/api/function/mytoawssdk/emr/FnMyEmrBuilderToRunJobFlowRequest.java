package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.emr;

import com.amazonaws.services.elasticmapreduce.model.*;
import com.fulmicotone.aws.cluster.cross.api.builder.MyCluster;
import com.fulmicotone.aws.cluster.cross.api.function.utils.FnFindAndReplacePlaceHolder;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyParams;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterHwConf;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterSecurityConf;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterSwConf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class FnMyEmrBuilderToRunJobFlowRequest implements BiFunction<MyCluster.MyClusterBuilder, MyParams, RunJobFlowRequest> {

    private FnFindAndReplacePlaceHolder fAndReplace = new FnFindAndReplacePlaceHolder();
    private FnMyEmrConfigurationToAWSConfiguration mapConfig = new FnMyEmrConfigurationToAWSConfiguration();

    private InstanceGroupConfig coreGroup = new InstanceGroupConfig()
            .withMarket(MarketType.SPOT)
            .withName("CORE").withInstanceRole(InstanceRoleType.CORE);
    private InstanceGroupConfig masterGroup = new InstanceGroupConfig()
            .withName("MASTER")
            .withInstanceRole(InstanceRoleType.MASTER)
            .withInstanceCount(1)
            .withMarket(MarketType.SPOT);

    private JobFlowInstancesConfig jobFlowConfigInstances = new JobFlowInstancesConfig();
    @Override
    public RunJobFlowRequest apply(MyCluster.MyClusterBuilder myClusterBuilder, MyParams pParams) {

        RunJobFlowRequest request = new RunJobFlowRequest().withName(myClusterBuilder.getName());;

        jobFlowConfigInstances.setInstanceGroups(new ArrayList(Arrays.asList(coreGroup,masterGroup)));

        request.setInstances(jobFlowConfigInstances);

        //map software configuration
        myClusterBuilder.getClusterSoftwareConf()
                .ifPresent(csw-> setupSoftwareOn(csw, request, pParams));

        //map hardware configuration
        myClusterBuilder.getClusterHardwareConf()
                .ifPresent(chw->setupHardwareOn(chw, request, pParams));

        //security configuration
        myClusterBuilder.getClusterSecurityConf()
                .ifPresent(cSecurity->setupSecurityOn(cSecurity, request, pParams));



        return request;
    }


    private void setupSecurityOn(ClusterSecurityConf swConf, RunJobFlowRequest r, MyParams p) {

       swConf.keyPair.ifNotNullRun(v-> jobFlowConfigInstances.setEc2KeyName(fAndReplace.apply(v, p)));
       swConf.resourceRole.ifNotNullRun(v-> r.withJobFlowRole(fAndReplace.apply(v, p)));
       swConf.role.ifNotNullRun(v-> r.withServiceRole(fAndReplace.apply(v, p)));
       swConf.subnetId.ifNotNullRun(v-> jobFlowConfigInstances.setEc2SubnetId(fAndReplace.apply(swConf.subnetId, p)));

    }

    private void setupHardwareOn(ClusterHwConf hardwareConf, RunJobFlowRequest request, MyParams p) {


       hardwareConf.coreInstanceCount
               .ifNotNullRun(v->
               coreGroup.withInstanceCount(Integer.parseInt(fAndReplace.apply(v, p))));

       hardwareConf.coreInstanceBidPrice
               .ifNotNullRun(v-> coreGroup.withBidPrice(fAndReplace.apply(v, p)));

       hardwareConf.coreInstanceType
               .ifNotNullRun(v-> coreGroup.setInstanceType(fAndReplace
                    .apply(hardwareConf.coreInstanceType, p)));

       hardwareConf.masterInstanceType
               .ifNotNullRun(v-> masterGroup.setInstanceType(fAndReplace
                    .apply(hardwareConf.masterInstanceType, p)));

       hardwareConf.masterInstanceBidPrice
               .ifNotNullRun(v->
            masterGroup.withBidPrice(fAndReplace
                    .apply(hardwareConf.masterInstanceBidPrice, p)));

    }

    private void setupSoftwareOn(ClusterSwConf swConf, RunJobFlowRequest request, MyParams pParams){

       swConf.releaseLabel.ifNotNullRun(v->
            request.withReleaseLabel(fAndReplace.apply(swConf.releaseLabel, pParams)));


       swConf.amiVersion.ifNotNullRun(v->
            request.withAmiVersion(fAndReplace.apply(swConf.amiVersion, pParams)));


       swConf.pipelineLogUri.ifNotNullRun(v->
            request.withLogUri(fAndReplace.apply(swConf.pipelineLogUri, pParams)));


        if (swConf.configuration.size() > 0) {
            request.withConfigurations(swConf.configuration.stream()
                    .map(c -> mapConfig.apply(c, pParams)).collect(Collectors.toList()));
        }

        if (swConf.coreGroupConfiguration.size() > 0) {

            coreGroup.setConfigurations(swConf.coreGroupConfiguration.stream()
                    .map(c -> mapConfig.apply(c, pParams)).collect(Collectors.toList()));
        }

        if (swConf.coreEbsConfiguration.size() > 0) {

            //don't know the right place to put ebs configuration in JobFlowRequest
                /*request.withConfigurations(swConf.coreEbsConfiguration.stream()
                        .map(c -> mapConfig.apply(c, pParams)).collect(Collectors.toList()));*/
        }

        swConf.applications.ifNotNullRun(v->{

            String applications = fAndReplace.apply(swConf.applications, pParams);

            List<Application> appList = Arrays.asList(applications.split(",")).stream()
                    .map(appAsString -> {
                        Application app = new Application();
                        app.setName(appAsString);
                        return app;
                    }).collect(Collectors.toList());

            request.withApplications(appList);

        });
    }
}
