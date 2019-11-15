package com.fulmicotone.aws.cluster.cross.api.spec.emr.business.conf.root;

import com.fulmicotone.aws.cluster.cross.api.models.Property;

import java.util.Collections;
import java.util.List;

public   class EmrSparkEnvConf extends EmrClusterConf {


    @Override
    String classification() {
        return "spark-env";
    }

}


