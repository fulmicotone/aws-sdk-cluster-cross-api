package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource;

import com.fulmicotone.aws.cluster.cross.api.models.fields.MyInteger;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;

public class ClusterHwConf {

    public MyString masterInstanceType;
    public MyString coreInstanceType;//x
    public MyString masterInstanceBidPrice;//x
    public MyInteger coreInstanceCount;//x
    public MyString coreInstanceBidPrice;


    public ClusterHwConf(){}


    public static final class ClusterHwConfBuilder {
        private MyString masterInstanceType;
        private MyString coreInstanceType;//x
        private MyString masterInstanceBidPrice;//x
        private MyInteger coreInstanceCount;//x
        private MyString coreInstanceBidPrice;

        private ClusterHwConfBuilder() {
        }

        public static ClusterHwConfBuilder newOne() {
            return new ClusterHwConfBuilder();
        }

        public ClusterHwConfBuilder withMasterInstanceType(MyString masterInstanceType) {
            this.masterInstanceType = masterInstanceType;
            return this;
        }

        public ClusterHwConfBuilder withCoreInstanceType(MyString coreInstanceType) {
            this.coreInstanceType = coreInstanceType;
            return this;
        }

        public ClusterHwConfBuilder withMasterInstanceBidPrice(MyString masterInstanceBidPrice) {
            this.masterInstanceBidPrice = masterInstanceBidPrice;
            return this;
        }

        public ClusterHwConfBuilder withCoreInstanceCount(MyInteger coreInstanceCount) {
            this.coreInstanceCount = coreInstanceCount;
            return this;
        }

        public ClusterHwConfBuilder withCoreInstanceBidPrice(MyString coreInstanceBidPrice) {
            this.coreInstanceBidPrice = coreInstanceBidPrice;
            return this;
        }

        public ClusterHwConf build() {
            ClusterHwConf clusterHwConf = new ClusterHwConf();
            clusterHwConf.coreInstanceCount = this.coreInstanceCount;
            clusterHwConf.coreInstanceType = this.coreInstanceType;
            clusterHwConf.coreInstanceBidPrice = this.coreInstanceBidPrice;
            clusterHwConf.masterInstanceType = this.masterInstanceType;
            clusterHwConf.masterInstanceBidPrice = this.masterInstanceBidPrice;
            return clusterHwConf;
        }
    }
}
