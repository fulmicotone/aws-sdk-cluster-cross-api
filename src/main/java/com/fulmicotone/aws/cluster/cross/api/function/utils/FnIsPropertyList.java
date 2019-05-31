package com.fulmicotone.aws.cluster.cross.api.function.utils;

import com.fulmicotone.aws.cluster.cross.api.models.Property;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.function.Predicate;

public class FnIsPropertyList implements Predicate<Field> {



    @Override
    public boolean test(Field listField) {
        ParameterizedType myType = (ParameterizedType) listField.getGenericType();
        Class<?> argClass = (Class<?>) myType.getActualTypeArguments()[0];
        return argClass== Property.class;

    }
}
