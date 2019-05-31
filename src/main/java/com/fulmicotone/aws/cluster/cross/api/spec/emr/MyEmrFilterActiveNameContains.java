package com.fulmicotone.aws.cluster.cross.api.spec.emr;

import com.amazonaws.services.elasticmapreduce.model.ClusterState;

public class MyEmrFilterActiveNameContains extends MyEmrFilterListRequest {


    public MyEmrFilterActiveNameContains(String name) {
        super();
         withClusterStates(ClusterState.RUNNING,
                 ClusterState.BOOTSTRAPPING,
                 ClusterState.STARTING,
                 ClusterState.WAITING);
         addFilter(cs->cs.getName().contains(name));
    }
}