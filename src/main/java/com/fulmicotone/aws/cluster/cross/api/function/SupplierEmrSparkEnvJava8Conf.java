package com.fulmicotone.aws.cluster.cross.api.function;

import com.fulmicotone.aws.cluster.cross.api.utils.Java8HomePropertySupplier;

public class SupplierEmrSparkEnvJava8Conf extends SupplierEmrEnvConf {

    public SupplierEmrSparkEnvJava8Conf() {
        super("spark-env",new Java8HomePropertySupplier().get());
    }
}
