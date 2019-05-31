package com.fulmicotone.aws.cluster.cross.api.function;

import com.fulmicotone.aws.cluster.cross.api.models.EmrConfiguration;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.emr.EmrSparkDefaultsProperties;

import java.util.function.Supplier;

public class SupplierParametrizableSparkDefaultsConfig implements Supplier<EmrConfiguration> {

    final static String spark_default="spark-defaults";

    @Override
    public EmrConfiguration get() {

        EmrConfiguration sparkDefault = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Configuration, spark_default);
        sparkDefault.classification = new MyString(spark_default);
        sparkDefault.property.add(EmrSparkDefaultsProperties.SPARK_EXECUTOR_INSTANCES);
        sparkDefault.property.add(EmrSparkDefaultsProperties.SPARK_EXECUTOR_MEMORY_OVERHEAD);
        sparkDefault.property.add(EmrSparkDefaultsProperties.SPARK_DRIVER_MEMORY_OVERHEAD);
        sparkDefault.property.add(EmrSparkDefaultsProperties.SPARK_EXECUTOR_MEMORY);
        sparkDefault.property.add(EmrSparkDefaultsProperties.SPARK_DRIVER_MEMORY);
        sparkDefault.property.add(EmrSparkDefaultsProperties.SPARK_EXECUTOR_CORE);
        sparkDefault.property.add(EmrSparkDefaultsProperties.SPARK_DRIVER_CORE);
        sparkDefault.property.add(EmrSparkDefaultsProperties.SPARK_DEFAULT_PARALLELISM);

        return sparkDefault;


    }
}
