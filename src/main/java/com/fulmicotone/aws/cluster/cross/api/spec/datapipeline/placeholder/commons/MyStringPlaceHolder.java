package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.commons;

import com.fulmicotone.aws.cluster.cross.api.models.enums.ParamsKeys;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;

public abstract  class MyStringPlaceHolder extends MyString {

    public MyStringPlaceHolder(String placeHolderVal) {
        throw new UnsupportedOperationException();
    }

    public MyStringPlaceHolder() {
        super();
        setPlaceHolder(placeHolderVal().name());
    }

    protected abstract ParamsKeys placeHolderVal();


}
