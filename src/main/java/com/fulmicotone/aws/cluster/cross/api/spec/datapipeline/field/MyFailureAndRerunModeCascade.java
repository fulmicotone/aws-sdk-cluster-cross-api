package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.field;

import com.fulmicotone.aws.cluster.cross.api.models.enums.FailureAndRerunMode;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyFailureAndRerunMode;

public class MyFailureAndRerunModeCascade extends MyFailureAndRerunMode {

    public MyFailureAndRerunModeCascade() { this.setVal(FailureAndRerunMode.cascade); }
}
