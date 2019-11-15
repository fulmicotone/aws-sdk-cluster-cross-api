package com.fulmicotone.aws.cluster.cross.api.spec.emr.business.conf.root;

import com.fulmicotone.aws.cluster.cross.api.models.EmrConfiguration;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;

public  abstract class EmrClusterConf {

    abstract String classification();


    public EmrConfiguration get() {

        EmrConfiguration env= MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Configuration, classification());
        env.classification=new MyString(classification());

        return env;
    }



    }
