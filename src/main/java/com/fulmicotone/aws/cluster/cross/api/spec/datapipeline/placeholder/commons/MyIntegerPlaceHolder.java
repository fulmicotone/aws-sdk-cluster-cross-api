package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.commons;

import com.fulmicotone.aws.cluster.cross.api.models.enums.ParamsKeys;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyInteger;

public abstract class  MyIntegerPlaceHolder extends MyInteger {

    public MyIntegerPlaceHolder(String val) {
        throw new UnsupportedOperationException();
    }

    public MyIntegerPlaceHolder() {
        super();
        setPlaceHolder(placeHolderVal().name());
    }


    protected abstract ParamsKeys placeHolderVal();



}
