package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline;

import com.fulmicotone.aws.cluster.cross.api.models.generic.IMyObject;

public class MyObjectBox<T extends IMyObject> {

    T obj;
    String propertyName;

    public MyObjectBox(T obj, String propertyName) {
        this.obj = obj;
        this.propertyName = propertyName;
    }


    public MyObjectBox(T obj) {
        this.obj = obj;
    }


    public T unwrap(){ return obj;}

    public String getPropertyName(){return  propertyName;}




}
