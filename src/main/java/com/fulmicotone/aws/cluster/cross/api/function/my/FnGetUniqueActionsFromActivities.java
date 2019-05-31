package com.fulmicotone.aws.cluster.cross.api.function.my;

import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSAction;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FnGetUniqueActionsFromActivities implements Function<List<AWSActivity>
        ,List<AWSAction>> {

    @Override
    public List<AWSAction> apply(List<AWSActivity> awsActivities) {

        return new ArrayList<>(awsActivities
                .stream()
                .flatMap(a -> Stream.of(a.onFail, a.onLateAction, a.onSuccess).filter(Objects::nonNull))
                .collect(Collectors.toMap(x -> x.id.getVal(), y -> y, (z, o) -> z))
                .values());
    }
}
