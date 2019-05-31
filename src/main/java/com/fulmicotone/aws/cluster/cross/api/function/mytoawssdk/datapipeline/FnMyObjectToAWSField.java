package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline;

import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipeLineObjectField;
import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipelineObject;

import java.lang.reflect.Field;
import java.util.function.BiFunction;

public class FnMyObjectToAWSField implements BiFunction<MyPipelineObject,
        Field,com.amazonaws.services.datapipeline.model.Field> {

    @Override
    public com.amazonaws.services.datapipeline.model.Field apply(MyPipelineObject myPipelineObject, Field field) {

        try {
            Object obj = field.get(myPipelineObject);

            if(obj instanceof MyPipelineObject){
                MyPipelineObject plo= (MyPipelineObject) obj;
               return new com.amazonaws.services.datapipeline.model.Field()
                        .withKey(field.getName()).withRefValue(plo.id.getVal());
            }

            else if (obj instanceof MyPipeLineObjectField){
                MyPipeLineObjectField plof= (MyPipeLineObjectField) obj;
                if(plof.isNull()){return null;}
                return new com.amazonaws.services.datapipeline.model.Field()
                        .withKey(field.getName()).withStringValue(plof.getVal());
            }
        } catch (IllegalAccessException e) { e.printStackTrace(); }
        return null;
    }
}
