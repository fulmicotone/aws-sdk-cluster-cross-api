package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.emr;

import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.model.ClusterSummary;
import com.amazonaws.services.elasticmapreduce.model.ListClustersResult;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.MyEmrFilterListRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

//tranform in list getting all cluster
public class FnGetEmrCluster
        implements BiFunction<AmazonElasticMapReduce,
        MyEmrFilterListRequest, List<ClusterSummary>> {
    @Override
    public  List<ClusterSummary> apply(AmazonElasticMapReduce emr,
                                       MyEmrFilterListRequest request) {
            ArrayList<ClusterSummary> r=new ArrayList<>();
            ListClustersResult result = emr.listClusters(request);
            do {
                r.addAll(result.getClusters().stream().filter(request::allMatch).collect(Collectors.toList()));
                request.withMarker(result.getMarker());
            }
            while ( (result.getMarker()!=null));
            return r;
    }
}
