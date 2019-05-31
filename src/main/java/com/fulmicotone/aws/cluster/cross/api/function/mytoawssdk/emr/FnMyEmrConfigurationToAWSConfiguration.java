package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.emr;

import com.amazonaws.services.elasticmapreduce.model.Configuration;
import com.fulmicotone.aws.cluster.cross.api.function.utils.FnFindAndReplacePlaceHolder;
import com.fulmicotone.aws.cluster.cross.api.models.EmrConfiguration;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyParams;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * This function convert from myEMRConfiguraiton to Amazon EMR configuration
 * replacing the place holder with the parameters
 */
public class FnMyEmrConfigurationToAWSConfiguration implements
        BiFunction<EmrConfiguration, MyParams,
        Configuration> {

   private FnFindAndReplacePlaceHolder fn_find_and_replace_place_holder=new FnFindAndReplacePlaceHolder();


    @Override
    public Configuration apply(EmrConfiguration inConf, MyParams pipelineParams) {

        Configuration outConf =new Configuration();

        //convert all inner config calling recursively it self
        List<Configuration> innerConfigs=inConf.configuration
                .stream()
                .map(c->apply(c,pipelineParams ))
                .collect(Collectors.toList());

        outConf.setClassification(fn_find_and_replace_place_holder.apply(inConf.classification, pipelineParams));

        outConf.setConfigurations(innerConfigs);

        inConf.property.forEach( prop->
                outConf.addPropertiesEntry(prop.key.getVal(),
                        fn_find_and_replace_place_holder
                                .apply(prop.value,pipelineParams))
        )   ;
        return outConf;
    }
}
