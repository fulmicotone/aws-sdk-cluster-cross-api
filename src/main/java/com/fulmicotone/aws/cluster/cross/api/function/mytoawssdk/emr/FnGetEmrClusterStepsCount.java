package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.emr;

import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.model.ListStepsRequest;
import com.amazonaws.services.elasticmapreduce.model.ListStepsResult;
import com.amazonaws.services.elasticmapreduce.model.StepState;

import java.util.function.BiFunction;

public class FnGetEmrClusterStepsCount
        implements BiFunction<AmazonElasticMapReduce, String,Integer> {


    @Override
    public Integer apply(AmazonElasticMapReduce emr, String clusterId) {
        int c=0;
        ListStepsRequest lsr = new ListStepsRequest()
                .withClusterId(clusterId)
                .withStepStates(StepState.PENDING);
        do {
            ListStepsResult r = emr.listSteps(lsr);
            c+= r.getSteps().size();
            lsr.withMarker(r.getMarker());

        }
        while ( lsr.getMarker()!=null);
        return c;
    }
}
