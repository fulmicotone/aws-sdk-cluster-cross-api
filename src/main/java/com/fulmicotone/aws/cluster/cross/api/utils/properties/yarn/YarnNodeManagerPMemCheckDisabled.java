package com.fulmicotone.aws.cluster.cross.api.utils.properties.yarn;

import com.fulmicotone.aws.cluster.cross.api.utils.properties.generic.YarnNodeManagerPMemCheckPropertySupplier;

public  class YarnNodeManagerPMemCheckDisabled extends YarnNodeManagerPMemCheckPropertySupplier {

    @Override
    protected String value() {
        return "false";
    }
}
