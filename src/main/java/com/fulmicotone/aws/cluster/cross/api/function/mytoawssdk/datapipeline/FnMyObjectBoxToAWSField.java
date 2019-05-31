package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline;

import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipeLineObjectField;
import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipelineObject;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyObjectBox;

import java.util.function.Function;

public class FnMyObjectBoxToAWSField implements Function<MyObjectBox,
        com.amazonaws.services.datapipeline.model.Field> {

    @Override
    public com.amazonaws.services.datapipeline.model.Field apply(MyObjectBox objBox) {

        Object refObj = objBox.unwrap();

        if(refObj instanceof MyPipelineObject){
            MyPipelineObject plo= (MyPipelineObject) refObj;
           return new com.amazonaws.services.datapipeline.model
                   .Field()
                    .withKey(objBox.getPropertyName())
                   .withRefValue(plo.id.getVal());
        }

        else if (refObj instanceof MyPipeLineObjectField){
            MyPipeLineObjectField plof= (MyPipeLineObjectField) refObj;
            if(plof.isNull()){return null;}
            return new com.amazonaws.services.datapipeline.model
                    .Field()
                    .withKey(objBox.getPropertyName())
                    .withStringValue(plof.getVal());
        }
        return null;
    }
}
