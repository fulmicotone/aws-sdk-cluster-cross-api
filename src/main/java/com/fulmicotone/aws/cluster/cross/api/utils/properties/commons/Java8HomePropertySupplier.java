package com.fulmicotone.aws.cluster.cross.api.utils.properties.commons;

import com.fulmicotone.aws.cluster.cross.api.utils.properties.generic.JavaHomePropertySupplier;

public  class Java8HomePropertySupplier extends JavaHomePropertySupplier {


    @Override
    protected String value() {
        return 	"/usr/lib/jvm/java-1.8.0";
    }
}
