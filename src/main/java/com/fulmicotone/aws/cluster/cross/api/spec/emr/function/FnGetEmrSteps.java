package com.fulmicotone.aws.cluster.cross.api.spec.emr.function;

import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.model.ListStepsRequest;
import com.amazonaws.services.elasticmapreduce.model.ListStepsResult;
import com.amazonaws.services.elasticmapreduce.model.StepState;
import com.amazonaws.services.elasticmapreduce.model.StepSummary;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class FnGetEmrSteps implements Function<AmazonElasticMapReduce,
        Function<ListStepsRequest,
        Function<Consumer<List<StepSummary>>,Void>>>{
    @Override
    public Function<ListStepsRequest, Function<Consumer<List<StepSummary>>, Void>>
    apply(AmazonElasticMapReduce emr) {
        return req -> stepListConsumer->{
            do {
                ListStepsResult r = emr.listSteps(req);
                stepListConsumer.accept(r.getSteps());
            req.withMarker(r.getMarker());}
            while ( req.getMarker()!=null);
            return null;
        };
    }
}
