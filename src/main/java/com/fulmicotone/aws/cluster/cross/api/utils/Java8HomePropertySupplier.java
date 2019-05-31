package com.fulmicotone.aws.cluster.cross.api.utils;

public  class Java8HomePropertySupplier extends JavaHomePropertySupplier {


    @Override
    protected String value() {
        return 	"/usr/lib/jvm/java-1.8.0";
    }
}
