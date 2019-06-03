package com.fulmicotone.aws.cluster.cross.api.spec.emr.business.getter.filters;

import com.amazonaws.services.elasticmapreduce.model.ClusterState;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.business.getter.filters.generic.MyEMRClusterFilterListRequest;

import java.time.temporal.ChronoUnit;
import java.util.Date;

public class MyEMRClusterFilterActiveAndCreationAfterNowMinus extends MyEMRClusterFilterListRequest {


    public MyEMRClusterFilterActiveAndCreationAfterNowMinus(int amount, ChronoUnit unit) {
        super();
        withClusterStates(ClusterState.RUNNING,
                ClusterState.BOOTSTRAPPING,
                ClusterState.STARTING,
                ClusterState.WAITING)
                            .withCreatedAfter(Date.from(new Date().toInstant().minus(1, ChronoUnit.HOURS)));
    }
}