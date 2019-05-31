package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline;

import com.amazonaws.services.datapipeline.model.Field;
import com.amazonaws.services.datapipeline.model.PipelineObject;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FnCreateAWSPipelineObjFromFields implements Function<List<Field>,PipelineObject> {

    @Override
    public PipelineObject apply(List<Field> fields) {

        String id = fields.stream().filter(field -> field.getKey().equals("id")).findFirst().get().getStringValue();
        String name=fields.stream().filter(field -> field.getKey().equals("name")).findFirst().get().getStringValue();
        List<Field> f = fields.stream()
                .filter(field -> !field.getKey().equals("name"))
                .filter(field -> !field.getKey().equals("id"))
                .collect(Collectors.toList());

        return new PipelineObject().withName(name).withId(id).withFields(f);
    }
}
