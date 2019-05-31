package com.fulmicotone.aws.cluster.cross.api.function.my;

import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipeLineObjectField;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Function;

public class FnMyPipelineObjectFieldToStr implements Function<MyPipeLineObjectField,String> {

    @Override
    public String apply(MyPipeLineObjectField myPipeLineObjectField) {


        try {
            Field getterVal = MyPipeLineObjectField.class.getDeclaredField("val");
            Field getterPH=MyPipeLineObjectField.class.getDeclaredField("placeHolder");
            getterPH.setAccessible(true);
            getterVal.setAccessible(true);
            Object value = getterVal.get(myPipeLineObjectField);
            return Optional.ofNullable(value).isPresent()?value.toString(): "#{"+Optional.of(getterPH).get()+"}";
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;

    }
}
