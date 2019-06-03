package com.fulmicotone.aws.cluster.cross.api.spec.emr.model;

import com.amazonaws.services.elasticmapreduce.model.ClusterSummary;
import com.amazonaws.services.elasticmapreduce.model.StepSummary;

import java.util.List;

public class MyClusterSummaryDeep {


    ClusterSummary clusterSummary;
    List<StepSummary> stepSummaryList;

    public MyClusterSummaryDeep(ClusterSummary clusterSummary, List<StepSummary> stepSummaryList) {
        this.clusterSummary = clusterSummary;
        this.stepSummaryList = stepSummaryList;
    }

    public ClusterSummary getClusterSummary() {
        return clusterSummary;
    }

    public List<StepSummary> getStepSummaryList() {
        return stepSummaryList;
    }
}
