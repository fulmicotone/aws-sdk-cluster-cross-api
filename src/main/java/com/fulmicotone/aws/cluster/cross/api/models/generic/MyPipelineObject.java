package com.fulmicotone.aws.cluster.cross.api.models.generic;

import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;

import java.io.Serializable;

public class MyPipelineObject implements Serializable,IMyObject {

    protected MyPipelineObject(){}


    public  MyString id;
    public  MyString name;

}
