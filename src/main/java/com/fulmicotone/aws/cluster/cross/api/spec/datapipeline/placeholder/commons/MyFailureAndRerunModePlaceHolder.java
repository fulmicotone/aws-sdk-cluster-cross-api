package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.commons;

import com.fulmicotone.aws.cluster.cross.api.models.enums.ParamsKeys;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyFailureAndRerunMode;

public abstract class MyFailureAndRerunModePlaceHolder extends MyFailureAndRerunMode {

    public MyFailureAndRerunModePlaceHolder(String val) {
        throw new UnsupportedOperationException();
    }

    public MyFailureAndRerunModePlaceHolder() {
        super();
        setPlaceHolder(placeHolderVal().name());
    }


    protected abstract ParamsKeys placeHolderVal();



}
