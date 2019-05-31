package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.emr;

import com.fulmicotone.aws.cluster.cross.api.function.my.FnPropertyFactory;
import com.fulmicotone.aws.cluster.cross.api.models.Property;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.key.*;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.value.*;

public class EmrSparkDefaultsProperties {

    final private static FnPropertyFactory fn_property_factory=new FnPropertyFactory();

    public static Property SPARK_EXECUTOR_CORE=fn_property_factory
            .apply(new ConfigKeySparkExecutorCores(),
                    new ConfigValueExecutorCorePH());

    public static Property SPARK_EXECUTOR_MEMORY=fn_property_factory
            .apply(new ConfigKeySparkExecutorMemory(),
                    new ConfigValueExecutorMemoryPH());

    public static Property SPARK_DYNAMIC_MAX_EXECUTOR=fn_property_factory
            .apply(new ConfigKeySparkDynamicMaxExecutors(),
                    new ConfigValueDynamicAllocationMaxExecutorsPH());


    public static Property SPARK_DRIVER_MEMORY=fn_property_factory
            .apply(new ConfigKeySparkDriverMemory(),
                    new ConfigValueDriverMemoryPH());


    public static Property SPARK_DRIVER_CORE=fn_property_factory
            .apply(new ConfigKeySparkDriverCores(),
                    new ConfigValueDriverCorePH());



    public static Property SPARK_EXECUTOR_INSTANCES=fn_property_factory
            .apply(new ConfigKeySparkExecutorInstances(),
                   new ConfigValueExecutorInstancesPH());

    public static Property SPARK_DRIVER_MEMORY_OVERHEAD=fn_property_factory
            .apply(new ConfigKeySparkDriverMemoryOverhead(),
                    new ConfigValueDriverMemoryOverheadPH());


    public static Property SPARK_EXECUTOR_MEMORY_OVERHEAD =fn_property_factory
            .apply(new ConfigKeySparkExecutorMemoryOverhead(),
                   new ConfigValueExecutorMemoryOverheadPH());


    public static Property SPARK_DEFAULT_PARALLELISM =fn_property_factory
            .apply(new ConfigKeySparkDefaultParallelism(),
                    new ConfigValueSparkDefaultParallelismPH());


}
