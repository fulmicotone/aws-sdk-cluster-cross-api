package com.fulmicotone.aws.cluster.cross.api.spec.emr.business.getter.filters.generic;

import com.amazonaws.services.elasticmapreduce.model.ClusterSummary;
import com.amazonaws.services.elasticmapreduce.model.ListStepsRequest;
import com.amazonaws.services.elasticmapreduce.model.StepSummary;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class MyEMRStepsListRequest extends ListStepsRequest {

    private  ClusterSummary cw;

    public MyEMRStepsListRequest(ClusterSummary  cw) {
       this(cw.getId());
        this.cw=cw;
    }
    public MyEMRStepsListRequest(String clusterId) {
        super();
        withClusterId(clusterId);
    }


    public Optional<ClusterSummary> getCw() {
        return Optional.ofNullable(cw);
    }

    private List<Predicate<StepSummary>> filterPredicates=new ArrayList<>();

    public MyEMRStepsListRequest addFilter(Predicate<StepSummary> predicate){
        filterPredicates.add(predicate);
        return this;
    }
    public boolean anyMatch(StepSummary cs){return filterPredicates.stream().anyMatch(f->f.test(cs));}

    public boolean allMatch(StepSummary cs){ return filterPredicates.stream().allMatch(f->f.test(cs));}

}
