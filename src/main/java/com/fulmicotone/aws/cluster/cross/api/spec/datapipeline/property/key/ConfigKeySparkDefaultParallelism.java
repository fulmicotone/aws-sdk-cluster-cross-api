package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.key;

import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.IPropertyKey;

public class ConfigKeySparkDefaultParallelism extends MyString implements IPropertyKey {


    public ConfigKeySparkDefaultParallelism() { super("spark.default.parallelism"); }
}
