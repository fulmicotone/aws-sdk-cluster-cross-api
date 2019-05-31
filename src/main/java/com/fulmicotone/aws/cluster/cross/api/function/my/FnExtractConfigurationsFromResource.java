package com.fulmicotone.aws.cluster.cross.api.function.my;

import com.fulmicotone.aws.cluster.cross.api.models.EmrCluster;
import com.fulmicotone.aws.cluster.cross.api.models.EmrConfiguration;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSConfiguration;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FnExtractConfigurationsFromResource implements Function<AWSResource, List<AWSConfiguration>> {
    @Override
    public List<AWSConfiguration>apply(AWSResource awsResource) {


        List<AWSConfiguration> rList=new ArrayList<>();


        //can grow based discovering object with configuration
        if(awsResource instanceof EmrCluster){
            EmrCluster c= (EmrCluster) awsResource;
            c.configuration.forEach(co-> reduceEMRConfTree(rList,co));
            c.coreEbsConfiguration.forEach(co-> reduceEMRConfTree(rList,co));
            c.coreGroupConfiguration.forEach(co-> reduceEMRConfTree(rList,co));
        }


        return rList;
    }

      void reduceEMRConfTree(List<AWSConfiguration> configAccumulator, EmrConfiguration tree){

            if(Optional.ofNullable(tree).isPresent()) {
                configAccumulator.add(tree);
                tree.configuration.forEach(conf -> reduceEMRConfTree(configAccumulator, conf));
            }
      }
}
