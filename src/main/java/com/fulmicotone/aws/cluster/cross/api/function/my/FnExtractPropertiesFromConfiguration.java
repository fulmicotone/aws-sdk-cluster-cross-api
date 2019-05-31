package com.fulmicotone.aws.cluster.cross.api.function.my;

import com.fulmicotone.aws.cluster.cross.api.models.EmrConfiguration;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSConfiguration;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FnExtractPropertiesFromConfiguration implements
        Function<AWSConfiguration, List<AWSProperty>> {

    @Override
    public List<AWSProperty> apply(AWSConfiguration awsConfiguration) {
        List<AWSProperty> rList=new ArrayList<>();
        if(awsConfiguration instanceof EmrConfiguration){ rList.addAll (((EmrConfiguration) awsConfiguration)
                .property); }
        return rList;
    }
}
