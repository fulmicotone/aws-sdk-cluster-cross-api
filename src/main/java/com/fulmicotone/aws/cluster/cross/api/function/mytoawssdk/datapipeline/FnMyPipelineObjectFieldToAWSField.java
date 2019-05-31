package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline;

import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipeLineObjectField;
import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipelineObject;

import java.util.function.BiFunction;

public class FnMyPipelineObjectFieldToAWSField implements BiFunction<String,Object,
        com.amazonaws.services.datapipeline.model.Field>{
    @Override
    public com.amazonaws.services.datapipeline.model.Field apply(String propertyName,
                                                                 Object obj) {
        if(obj instanceof MyPipelineObject){
            MyPipelineObject plo= (MyPipelineObject) obj;
            return new com.amazonaws.services.datapipeline.model.Field()
                    .withKey(propertyName).withRefValue(plo.id.getVal());
        }

        else if (obj instanceof MyPipeLineObjectField){
            MyPipeLineObjectField plof= (MyPipeLineObjectField) obj;
            if(plof.isNull()){return null;}
            return new com.amazonaws.services.datapipeline.model.Field()
                    .withKey(propertyName).withStringValue(plof.getVal());
        }

        return null;
    }

}