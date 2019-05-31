package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource;

import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;

public class ClusterSecurityConf {

    public MyString subnetId;
    public MyString region;
    public MyString additionalMasterSecurityGroupIds;
    public MyString additionalSlaveSecurityGroupIds;
    public MyString keyPair;
    public MyString availabilityZone;
    public  MyString resourceRole;
    public  MyString role;

    public static final class ClusterSecuityConfBuilder {

        private MyString subnetId;
        private MyString region;
        private MyString additionalMasterSecurityGroupIds;
        private MyString additionalSlaveSecurityGroupIds;
        private MyString keyPair;
        private MyString availabilityZone;
        private  MyString resourceRole;
        private  MyString role;

        private ClusterSecuityConfBuilder() {
        }

        public static ClusterSecuityConfBuilder newOne() {
            return new ClusterSecuityConfBuilder();
        }

        public ClusterSecuityConfBuilder withSubnetId(MyString subnetId) {
            this.subnetId = subnetId;
            return this;
        }

        public ClusterSecuityConfBuilder withRegion(MyString region) {
            this.region = region;
            return this;
        }

        public ClusterSecuityConfBuilder withAdditionalMasterSecurityGroupIds(MyString additionalMasterSecurityGroupIds) {
            this.additionalMasterSecurityGroupIds = additionalMasterSecurityGroupIds;
            return this;
        }

        public ClusterSecuityConfBuilder withAdditionalSlaveSecurityGroupIds(MyString additionalSlaveSecurityGroupIds) {
            this.additionalSlaveSecurityGroupIds = additionalSlaveSecurityGroupIds;
            return this;
        }

        public ClusterSecuityConfBuilder withKeyPair(MyString keyPair) {
            this.keyPair = keyPair;
            return this;
        }

        public ClusterSecuityConfBuilder withAvailabilityZone(MyString availabilityZone) {
            this.availabilityZone = availabilityZone;
            return this;
        }

        public ClusterSecuityConfBuilder withResourceRole(MyString resourceRole) {
            this.resourceRole = resourceRole;
            return this;
        }

        public ClusterSecuityConfBuilder withRole(MyString role) {
            this.role = role;
            return this;
        }

        public ClusterSecurityConf build() {
            ClusterSecurityConf clusterSecuityConf = new ClusterSecurityConf();
            clusterSecuityConf.resourceRole = this.resourceRole;
            clusterSecuityConf.role = this.role;
            clusterSecuityConf.keyPair = this.keyPair;
            clusterSecuityConf.subnetId = this.subnetId;
            clusterSecuityConf.additionalSlaveSecurityGroupIds = this.additionalSlaveSecurityGroupIds;
            clusterSecuityConf.additionalMasterSecurityGroupIds = this.additionalMasterSecurityGroupIds;
            clusterSecuityConf.region = this.region;
            clusterSecuityConf.availabilityZone = this.availabilityZone;
            return clusterSecuityConf;
        }
    }
}
