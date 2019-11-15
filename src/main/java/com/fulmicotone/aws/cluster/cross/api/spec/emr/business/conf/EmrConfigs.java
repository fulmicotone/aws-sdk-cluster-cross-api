package com.fulmicotone.aws.cluster.cross.api.spec.emr.business.conf;

import com.fulmicotone.aws.cluster.cross.api.models.EmrConfiguration;
import com.fulmicotone.aws.cluster.cross.api.models.Property;
import com.fulmicotone.aws.cluster.cross.api.spec.emr.business.conf.root.*;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class EmrConfigs {

    public static EmrConfiguration sparkEnv(Supplier<Property> ... suppliers){
        EmrConfiguration export=new EmrExportConf().get();
        EmrConfiguration root = new EmrSparkEnvConf().get();
        Stream.of(suppliers).map(Supplier::get).forEach(export.property::add);
        root.configuration.add(export);
        return root;
    }

    public static EmrConfiguration hadoopEnv(Supplier<Property> ... suppliers){
        EmrConfiguration export=new EmrExportConf().get();
        EmrConfiguration root = new EmrHadoopEnvConf().get();
        Stream.of(suppliers).map(Supplier::get).forEach(export.property::add);
        root.configuration.add(export);
        return root;
    }

    public static EmrConfiguration yarnSite(Supplier<Property> ... suppliers){

        EmrConfiguration root = new EmrYarnSiteConf().get();
        Stream.of(suppliers).map(Supplier::get).forEach(root.property::add);

        return root;
    }

    public static EmrConfiguration spark(Supplier<Property> ... suppliers){

        EmrConfiguration root = new EmrSparkConf().get();
        Stream.of(suppliers).map(Supplier::get).forEach(root.property::add);

        return root;
    }
}
