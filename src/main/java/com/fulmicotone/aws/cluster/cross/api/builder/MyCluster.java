package com.fulmicotone.aws.cluster.cross.api.builder;


import com.fulmicotone.aws.cluster.cross.api.models.EmrCluster;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterBehaviourConf;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterHwConf;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterSecurityConf;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterSwConf;

import java.util.Objects;
import java.util.Optional;

public class MyCluster {

    public String id;
    public String name;
    public ClusterBehaviourConf clusterBehaviourConf ;
    public ClusterHwConf clusterHwConf ;
    public ClusterSwConf clusterSwConf ;
    public ClusterSecurityConf clusterSecuityConf ;

    public static final class MyClusterBuilder {

        public String getName() { return name; }

        public Optional<ClusterBehaviourConf> getClusterBehaviourConf() {
            return Optional.ofNullable(clusterBehaviourConf);
        }

        public Optional<ClusterHwConf >getClusterHardwareConf() {
            return Optional.ofNullable(clusterHwConf);
        }

        public Optional<ClusterSwConf> getClusterSoftwareConf() { return Optional.ofNullable(clusterSwConf); }

        public Optional<ClusterSecurityConf> getClusterSecurityConf(){return Optional.ofNullable(clusterSecurityConf);}

        private ClusterBehaviourConf clusterBehaviourConf;
        private ClusterHwConf clusterHwConf;
        private ClusterSwConf clusterSwConf;
        private ClusterSecurityConf clusterSecurityConf;
        private String id;
        private String name;

        private MyClusterBuilder() { }

        public static MyClusterBuilder newOne() {
            return new MyClusterBuilder();
        }

        public MyClusterBuilder withClusterBehaviourConf(ClusterBehaviourConf clusterBehaviourConf) {
            this.clusterBehaviourConf = clusterBehaviourConf;
            return this;
        }

        public MyClusterBuilder withClusterHwConf(ClusterHwConf clusterHwConf) {
            this.clusterHwConf = clusterHwConf;
            return this;
        }

        public MyClusterBuilder withClusterSwConf(ClusterSwConf clusterSwConf) {
            this.clusterSwConf = clusterSwConf;
            return this;
        }

        public MyClusterBuilder withClusterSecurityConf(ClusterSecurityConf clusterSecuityConf) {
            this.clusterSecurityConf = clusterSecuityConf;
            return this;
        }
        public MyClusterBuilder withTag(String id){
            this.id=id;
            this.name=id;
            return this;
        }


        public MyClusterBuilder withTag(String id, String name){
            this.id=id;
            this.name=name;
            return this;
        }


        public EmrCluster build() {
            Objects.requireNonNull(id,"cluster id  is a required field" );
            Objects.requireNonNull(clusterHwConf,"cluster Hardware configurations is a required field" );
            Objects.requireNonNull(clusterSecurityConf,"cluster Security configurations is a required field" );

            EmrCluster emrCluster = MyPipelineObjectFactory
                    .newObject(PipelineObjectsClasses.EmrCluster,
                            id,
                            name);


            if(Optional.ofNullable(this.clusterBehaviourConf).isPresent()) {
                emrCluster.terminateAfter = this.clusterBehaviourConf.terminateAfter;
                emrCluster.endDateTime = this.clusterBehaviourConf.endDateTime;
                emrCluster.occurrences = this.clusterBehaviourConf.occurrences;
                emrCluster.attemptStatus = this.clusterBehaviourConf.attemptStatus;
                emrCluster.actionOnResourceFailure = this.clusterBehaviourConf.actionOnResourceFailure;
                emrCluster.attemptTimeout = this.clusterBehaviourConf.attemptTimeout;
                emrCluster.maximumRetries = this.clusterBehaviourConf.maximumRetries;
                emrCluster.bootstrapAction = this.clusterBehaviourConf.bootstrapAction;
                emrCluster.failureAndRerunMode = this.clusterBehaviourConf.failureAndRerunMode;
            }


            emrCluster.masterInstanceType = this.clusterHwConf.masterInstanceType;
            emrCluster.coreInstanceType = this.clusterHwConf.coreInstanceType;
            emrCluster.masterInstanceBidPrice = this.clusterHwConf.masterInstanceBidPrice;
            emrCluster.coreInstanceCount = this.clusterHwConf.coreInstanceCount;
            emrCluster.coreInstanceBidPrice = this.clusterHwConf.coreInstanceBidPrice;


            emrCluster.subnetId = this.clusterSecurityConf.subnetId;
            emrCluster.region = this.clusterSecurityConf.region;
            emrCluster.additionalMasterSecurityGroupIds = this.clusterSecurityConf.additionalMasterSecurityGroupIds;
            emrCluster.additionalSlaveSecurityGroupIds = this.clusterSecurityConf.additionalSlaveSecurityGroupIds;
            emrCluster.keyPair = this.clusterSecurityConf.keyPair;
            emrCluster.availabilityZone = this.clusterSecurityConf.availabilityZone;
            emrCluster.resourceRole = this.clusterSecurityConf.resourceRole;
            emrCluster.role = this.clusterSecurityConf.role;


            if(Optional.ofNullable(this.clusterSwConf).isPresent()) {
                emrCluster.amiVersion = this.clusterSwConf.amiVersion;
                emrCluster.applications = this.clusterSwConf.applications;
                emrCluster.configuration = this.clusterSwConf.configuration;
                emrCluster.coreGroupConfiguration = this.clusterSwConf.coreGroupConfiguration;
                emrCluster.coreEbsConfiguration = this.clusterSwConf.coreEbsConfiguration;
                emrCluster.releaseLabel = this.clusterSwConf.releaseLabel;
                emrCluster.pipelineLogUri = this.clusterSwConf.pipelineLogUri;
            }

            return emrCluster;
        }
    }

}
