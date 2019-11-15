package com.fulmicotone.aws.cluster.cross.api.utils.properties.generic;

import com.fulmicotone.aws.cluster.cross.api.models.Property;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;

import java.util.function.Supplier;

public abstract class YarnNodeManagerPMemCheckPropertySupplier implements Supplier<Property> {

    protected static String key="yarn.nodemanager.pmem-check-enabled";

    protected  abstract  String value();

    @Override
    public Property get() {

        Property p= MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Property, key);
        p.key.setVal(key);
        p.value.setVal(value());

        return p;
    }
}
