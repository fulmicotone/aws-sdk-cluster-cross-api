package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline;

import com.fulmicotone.aws.cluster.cross.api.models.Property;
import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipelineObject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class FnPropertyListToAWSField implements BiFunction<MyPipelineObject,
        Field,List<com.amazonaws.services.datapipeline.model.Field>> {


    final String property_label="property";
    @Override
    public List<com.amazonaws.services.datapipeline.model.Field >
    apply(MyPipelineObject myPipelineObject, Field fieldList) {
        try {
            List<Property> obj = (List<Property>) fieldList.get(myPipelineObject);

            return obj.stream()
                    .map(prop->new FnMyPipelineObjectFieldToAWSField()
                            .apply(property_label, prop)).collect(Collectors.toList());

        } catch (IllegalAccessException e) { e.printStackTrace(); }
        return null;
    }
}
