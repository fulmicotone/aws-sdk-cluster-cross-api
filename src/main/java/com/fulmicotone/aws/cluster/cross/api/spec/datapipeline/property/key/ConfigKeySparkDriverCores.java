package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.key;

import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.IPropertyKey;

public class ConfigKeySparkDriverCores extends MyString implements IPropertyKey {


    public ConfigKeySparkDriverCores() { super("spark.driver.cores"); }
}
