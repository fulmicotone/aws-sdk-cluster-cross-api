package com.fulmicotone.aws.cluster.cross.api.utils.properties.spark;

import com.fulmicotone.aws.cluster.cross.api.models.Property;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;

import java.util.function.Supplier;

public abstract class SparkMaxResourceAllocationPropertySupplier implements Supplier<Property> {

    protected static String key="maximizeResourceAllocation";

    @Override
    public Property get() {

        Property prop= MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Property, key);
        prop.key.setVal(key);
        prop.value.setVal("true");
        return prop;
    }
}
