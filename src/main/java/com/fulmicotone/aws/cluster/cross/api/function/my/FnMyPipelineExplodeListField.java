package com.fulmicotone.aws.cluster.cross.api.function.my;


import com.fulmicotone.aws.cluster.cross.api.models.generic.IMyObject;
import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipelineObject;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyObjectBox;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class FnMyPipelineExplodeListField implements BiFunction<MyPipelineObject, Field,List<MyObjectBox>> {


    @Override
    public List<MyObjectBox> apply(MyPipelineObject po, Field f) {
        try {

         return  f.getType() == List.class? (List<MyObjectBox>) ((List)f.get(po)).stream()
                     .filter(x-> x instanceof IMyObject)
                    .map(x-> new MyObjectBox((IMyObject) x,f.getName()))
                 .collect(Collectors.toList()): Arrays.asList(new MyObjectBox((IMyObject) f.get(po),f.getName()));

        } catch (IllegalAccessException e) { return null; }


    }

}
