package com.fulmicotone.aws.cluster.cross.api.function.my;

import com.fulmicotone.aws.cluster.cross.api.models.Property;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.IPropertyKey;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.IPropertyValue;

import java.util.function.BiFunction;

public class FnPropertyFactory implements BiFunction<IPropertyKey, IPropertyValue, Property> {

    @Override
    public Property apply(IPropertyKey key, IPropertyValue value) {
        Property p = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Property, key.getVal());
        p.key= (MyString) key;
        p.value = (MyString) value;
        return p;
    }
}
