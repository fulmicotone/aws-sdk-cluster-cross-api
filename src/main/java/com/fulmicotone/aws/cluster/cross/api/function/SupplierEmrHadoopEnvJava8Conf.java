package com.fulmicotone.aws.cluster.cross.api.function;

import com.fulmicotone.aws.cluster.cross.api.utils.Java8HomePropertySupplier;

public class SupplierEmrHadoopEnvJava8Conf extends SupplierEmrEnvConf {

    public SupplierEmrHadoopEnvJava8Conf() {
        super("hadoop-env",new Java8HomePropertySupplier().get());
    }
}
