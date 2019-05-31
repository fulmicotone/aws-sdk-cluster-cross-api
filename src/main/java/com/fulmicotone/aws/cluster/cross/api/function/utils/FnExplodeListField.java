package com.fulmicotone.aws.cluster.cross.api.function.utils;


import com.fulmicotone.aws.cluster.cross.api.utils.FnNewReflectField;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class FnExplodeListField implements BiFunction<Object, java.lang.reflect.Field,List<Field>> {




    @Override
    public List<Field> apply(Object o, java.lang.reflect.Field f) {


        try {

         return (List<Field>) ((List)f.get(o)).stream()
                    .map(x-> FnNewReflectField
                            .Param.Builder.newOne()
                            .withName(f.getName())
                                .withDeclaringClass(f.getDeclaringClass())
                                .withType(x.getClass())
                            .build()
                            .apply())
                 .collect(Collectors.toList());

        } catch (IllegalAccessException e) {

            return null;

        }


    }

}
