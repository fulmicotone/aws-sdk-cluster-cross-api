package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.emr;

import com.fulmicotone.aws.cluster.cross.api.models.enums.ParamsKeys;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.commons.MyFailureAndRerunModePlaceHolder;

public class FailureAndRerunModePH extends MyFailureAndRerunModePlaceHolder {
    @Override
    protected ParamsKeys placeHolderVal() {
        return ParamsKeys.myFailureAndRerunMode;
    }
}
