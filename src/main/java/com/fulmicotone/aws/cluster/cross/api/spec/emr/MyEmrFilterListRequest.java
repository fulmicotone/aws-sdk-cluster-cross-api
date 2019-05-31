package com.fulmicotone.aws.cluster.cross.api.spec.emr;

import com.amazonaws.services.elasticmapreduce.model.ClusterSummary;
import com.amazonaws.services.elasticmapreduce.model.ListClustersRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MyEmrFilterListRequest extends ListClustersRequest {

    private  List<Predicate<ClusterSummary>> filterPredicates=new ArrayList<>();

    public MyEmrFilterListRequest addFilter(Predicate<ClusterSummary> predicate){
        filterPredicates.add(predicate);
        return this;
    }
    public boolean anyMatch(ClusterSummary cs){return filterPredicates.stream().anyMatch(f->f.test(cs));}

    public boolean allMatch(ClusterSummary cs){ return filterPredicates.stream().allMatch(f->f.test(cs));}
}