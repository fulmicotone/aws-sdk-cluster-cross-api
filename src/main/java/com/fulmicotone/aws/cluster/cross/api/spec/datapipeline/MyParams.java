package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline;

import com.fulmicotone.aws.cluster.cross.api.models.enums.ParamsKeys;

import java.util.HashMap;
import java.util.Optional;

public class MyParams extends HashMap<String, Object> {

    private EmrResource emrResource;

    public EmrResource emrResource(){
        return Optional.ofNullable(emrResource)
                .orElseGet(()-> (emrResource=new EmrResource()));}

    /** when we'll covered all the keys we will disable free key add*/
    /*@Override
    public Object put(String key, Object value) {
        throw new UnsupportedOperationException();
    }*/
    private MyParams put(ParamsKeys key, String value) {
        put(key.name(), value);
        return this;
    }

    public class EmrResource {

        public EmrResource() { }


        public EmrResource withConfigExecutorInstances(int instances) {
            put(ParamsKeys.myEmrConfigExecutorInstances, instances + "");
            return this;
        }

        public EmrResource withConfigDriverOverhead(int megabytes) {
            put(ParamsKeys.myEmrConfigDriverOverhead, megabytes + "");
            return this;
        }
        public EmrResource withConfigExecutorOverhead(int megabytes) {
            put(ParamsKeys.myEmrConfigExecutorOverhead, megabytes + "");
            return this;
        }

        public EmrResource withConfigExecutorMemory(Integer memoryInGb) {
            put(ParamsKeys.myEmrConfigExecutorMemory, memoryInGb + "G");
            return this;
        }


        public EmrResource withConfigDriverMemory(Integer memoryInGb) {
            put(ParamsKeys.myEmrConfigDriverMemory, memoryInGb + "G");
            return this;
        }

        public EmrResource withConfigExecutorCore(Integer executorCore) {
            put(ParamsKeys.myEmrConfigExecutorCore, executorCore + "");
            return this;
        }

        public EmrResource withConfigDriverCore(Integer driverCores) {
            put(ParamsKeys.myEmrConfigDriverCore, driverCores + "");
            return this;
        }


        public EmrResource withConfigDefaultParallelism(Integer defaultParallelism) {
            put(ParamsKeys.myEmrConfigDefaultParallelism, defaultParallelism + "");
            return this;
        }

        public EmrResource withConfigDynamicAllocatioNMaxExecutors(Integer maxExecutors) {
            put(ParamsKeys.myEmrConfigDynamicAllocationMaxExecutors, maxExecutors.toString());
            return this;
        }

        public EmrResource withCoreType(String coreType) {
            put(ParamsKeys.myEmrCoreType, coreType);
            return this;
        }

        public EmrResource withMasterType(String masterType) {
            put(ParamsKeys.myEmrMasterType, masterType);
            return this;
        }

        public EmrResource withCoreCount(Integer coreCount) {
            put(ParamsKeys.myEmrCoreCount, coreCount.toString());
            return this;
        }

        public EmrResource withMasterBid(Float bidPrice) {
            put(ParamsKeys.myEmrMasterBid, bidPrice.toString());
            return this;
        }

        public EmrResource withCoreBid(Float bidPrice) {
            put(ParamsKeys.myEmrCoreBid, bidPrice.toString());
            return this;
        }

        public EmrResource withKeyPair(String keyPair) {
            put(ParamsKeys.myEmrKeyPair, keyPair);
            return this;
        }

        public EmrResource withSubnet(String subnet) {
            put(ParamsKeys.myEmrSubnet, subnet);
            return this;
        }

        public EmrResource withRole(String role) {
            put(ParamsKeys.myEmrRole, role);
            return this;
        }

        public EmrResource withResourceRole(String resourceRole) {
            put(ParamsKeys.myEmrResourceRole, resourceRole);
            return this;
        }

        public EmrResource withApplications(String applications) {
            put(ParamsKeys.myEmrApplications, applications);
            return this;
        }

        public EmrResource withReleaseLabel(String releaseLabel) {
            put(ParamsKeys.myEmrReleaseLabel, releaseLabel);
            return this;
        }

            public EmrResource withRegion(String region) {
                put(ParamsKeys.myEmrRegion, region);
                return this;
        }


        public EmrResource withLogPipelineURI(String logPipelineURI) {
            put(ParamsKeys.myEmrLogPipelineURI, logPipelineURI);
            return this;
        }
    }

}
