package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline;

import com.amazonaws.services.datapipeline.model.PipelineObject;
import com.fulmicotone.aws.cluster.cross.api.MyPipeline;
import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipelineObject;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FnMyPipelineToAWSPipelineObjectList implements Function<
        MyPipeline,
        List<PipelineObject>> {


    private <E extends MyPipelineObject>Stream<PipelineObject> toStream(List<E> objects)
    {
        return objects.stream()
                .map(new FnMyPipelineObjectToAWSFields())
                .map(new FnCreateAWSPipelineObjFromFields())
                .collect(Collectors.toList()).<PipelineObject>stream();
    }


    @Override
    public List<PipelineObject> apply(MyPipeline myPipeline) {


        List<PipelineObject> pipeObjects = Stream.of(toStream(myPipeline.resources),
                toStream(myPipeline.actions),
                toStream(myPipeline.activityList),
                toStream(myPipeline.datanodeList),
                toStream(myPipeline.configurations),
                toStream(myPipeline.properties))
                .reduce(Stream::concat)
                .orElseGet(Stream::empty)
                .collect(Collectors.toList());

        pipeObjects.add(new FnMyPipelineObjectToAWSFields()
                .andThen(new FnCreateAWSPipelineObjFromFields())
                .apply(myPipeline.aDefault));

        return pipeObjects;
    }
}
