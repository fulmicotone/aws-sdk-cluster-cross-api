package com.fulmicotone.aws.cluster.cross.api.utils.properties.generic;

import com.fulmicotone.aws.cluster.cross.api.models.Property;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;

import java.util.function.Supplier;

public abstract class JavaHomePropertySupplier implements Supplier<Property> {

    protected static String key="JAVA_HOME";

    protected  abstract  String value();

    @Override
    public Property get() {

        Property javaHomeProp= MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Property,
                key);
        javaHomeProp.key.setVal(key);
        javaHomeProp.value.setVal(value());

        return javaHomeProp;
    }
}
