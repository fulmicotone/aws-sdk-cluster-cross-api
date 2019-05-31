package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.value;

import com.fulmicotone.aws.cluster.cross.api.models.enums.ParamsKeys;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.commons.MyStringPlaceHolder;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.property.IPropertyValue;

public class ConfigValueSparkDefaultParallelismPH extends MyStringPlaceHolder implements IPropertyValue {
    @Override
    protected ParamsKeys placeHolderVal() {
        return ParamsKeys.myEmrConfigDefaultParallelism;
    }
}
