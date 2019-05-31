package com.fulmicotone.aws.cluster.cross.api.spec.emr;

import com.amazonaws.services.elasticmapreduce.model.ClusterState;

import java.time.temporal.ChronoUnit;
import java.util.Date;

public class MyEmrFilterActiveAndCreationAfterNowMinus extends MyEmrFilterListRequest {


    public MyEmrFilterActiveAndCreationAfterNowMinus(int amount, ChronoUnit unit) {
        super();
        withClusterStates(ClusterState.RUNNING,
                ClusterState.BOOTSTRAPPING,
                ClusterState.STARTING,
                ClusterState.WAITING)
                            .withCreatedAfter(Date.from(new Date().toInstant().minus(1, ChronoUnit.HOURS)));
    }
}