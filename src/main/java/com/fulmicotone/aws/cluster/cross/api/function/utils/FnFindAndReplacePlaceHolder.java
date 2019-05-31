package com.fulmicotone.aws.cluster.cross.api.function.utils;

import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipeLineObjectField;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyParams;

import java.util.Optional;
import java.util.function.BiFunction;

public class FnFindAndReplacePlaceHolder implements BiFunction<MyPipeLineObjectField, MyParams,String> {

    @Override
    public String apply(MyPipeLineObjectField myPipeLineObjectField, MyParams pipelineParams) {
        if(myPipeLineObjectField.isNull()){
            throw new IllegalArgumentException("the field passed must have value or placeHolder initialized");}
            try {
            return   Optional.ofNullable(myPipeLineObjectField.getVal())
                        .map(v -> myPipeLineObjectField.isPlaceHolder() ?
                                (String) pipelineParams.get(v
                                        .replace("#{", "")
                                        .replace("}", "")) : v).get();
            }catch (Exception e){
                throw new
                        IllegalArgumentException("value for the placeHolder:"+myPipeLineObjectField.getVal()+
                        " not found in map");
            }
    }
}
