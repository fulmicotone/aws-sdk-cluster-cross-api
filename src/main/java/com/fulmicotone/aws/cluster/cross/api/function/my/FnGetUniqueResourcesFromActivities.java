package com.fulmicotone.aws.cluster.cross.api.function.my;

import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSActivity;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSResource;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FnGetUniqueResourcesFromActivities implements Function<List<AWSActivity>
        ,List<AWSResource>> {

    @Override
    public List<AWSResource> apply(List<AWSActivity> awsActivities) {

        return awsActivities.stream()
                .filter(x->x.runsOn!=null)
                .collect(Collectors.toMap(x-> x.runsOn.id.getVal(), y->y.runsOn,(z,o)->z ) )
                .values().stream().collect(Collectors.toList());
    }
}
