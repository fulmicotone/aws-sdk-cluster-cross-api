package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.emr;

import com.fulmicotone.aws.cluster.cross.api.models.enums.ParamsKeys;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.commons.MyStringPlaceHolder;

public class LogPipelineUriPH extends MyStringPlaceHolder {
    @Override
    protected ParamsKeys placeHolderVal() {
        return ParamsKeys.myEmrLogPipelineURI;
    }
}
