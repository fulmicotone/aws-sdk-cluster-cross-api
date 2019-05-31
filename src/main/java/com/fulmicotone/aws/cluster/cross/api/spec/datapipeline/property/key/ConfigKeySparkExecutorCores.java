package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.key;

import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.IPropertyKey;

public class ConfigKeySparkExecutorCores extends MyString implements IPropertyKey {


    public ConfigKeySparkExecutorCores() { super("spark.executor.cores"); }
}
