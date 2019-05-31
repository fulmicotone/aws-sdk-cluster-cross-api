package com.fulmicotone.aws.cluster.cross.api.function;

import com.fulmicotone.aws.cluster.cross.api.models.EmrConfiguration;
import com.fulmicotone.aws.cluster.cross.api.models.Property;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;

import java.util.function.Supplier;

public class SupplierEmrEnvConf implements Supplier<EmrConfiguration> {


    private final static String exportClassification="export";
    private final Property[] properties;
    private final String rootClassification;

    public SupplierEmrEnvConf(String rootClassification, Property... properties){
        this.rootClassification=rootClassification;
        this.properties=properties; }
    @Override
    public EmrConfiguration get() {
        EmrConfiguration env= MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Configuration, rootClassification);
        EmrConfiguration exportConf= MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Configuration, exportClassification);
        env.classification=new MyString(rootClassification);
        exportConf.classification=new MyString(exportClassification);
        for(int i=0; i<properties.length;i++){ exportConf.property.add(properties[i]);}
        env.configuration.add(exportConf);
        return env;
    }
}
