package com.fulmicotone.aws.cluster.cross.api.spec.emr.business.getter.filters;

import com.amazonaws.services.elasticmapreduce.model.ClusterState;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.business.getter.filters.generic.MyEMRClusterFilterListRequest;

public class MyEMRClusterFilterActiveNameContains extends MyEMRClusterFilterListRequest {


    public MyEMRClusterFilterActiveNameContains(String name) {
        super();
         withClusterStates(ClusterState.RUNNING,
                 ClusterState.BOOTSTRAPPING,
                 ClusterState.STARTING,
                 ClusterState.WAITING);
         addFilter(cs->cs.getName().contains(name));
    }
}