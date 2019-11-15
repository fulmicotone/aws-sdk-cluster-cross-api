package com.fulmicotone.aws.cluster.cross.api.utils.properties.yarn;

import com.fulmicotone.aws.cluster.cross.api.utils.properties.generic.YarnNodeManagerVMemCheckPropertySupplier;

public  class YarnNodeManagerVMemCheckDisabled extends YarnNodeManagerVMemCheckPropertySupplier {

    @Override
    protected String value() {
        return "false";
    }
}
