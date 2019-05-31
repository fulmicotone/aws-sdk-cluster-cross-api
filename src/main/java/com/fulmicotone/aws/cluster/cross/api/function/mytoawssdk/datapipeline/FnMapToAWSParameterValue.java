package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline;

import com.amazonaws.services.datapipeline.model.ParameterValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FnMapToAWSParameterValue implements Function<Map<String,Object>,List<ParameterValue>> {

    @Override
    public List<ParameterValue> apply(Map<String, Object> stringObjectMap) {
        List<ParameterValue> list=new ArrayList<>();
        stringObjectMap.forEach((k,v)-> list.add(new ParameterValue().withId(k).withStringValue(v.toString())));
        return list;
    }
}
