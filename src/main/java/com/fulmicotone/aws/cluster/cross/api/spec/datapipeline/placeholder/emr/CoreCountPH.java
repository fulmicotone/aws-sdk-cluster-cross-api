package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.emr;

import com.fulmicotone.aws.cluster.cross.api.models.enums.ParamsKeys;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.commons.MyIntegerPlaceHolder;

public class CoreCountPH extends MyIntegerPlaceHolder {
    @Override
    protected ParamsKeys placeHolderVal() {
        return ParamsKeys.myEmrCoreCount;
    }
}
