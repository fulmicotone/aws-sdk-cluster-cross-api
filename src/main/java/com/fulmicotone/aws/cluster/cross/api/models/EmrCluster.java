package com.fulmicotone.aws.cluster.cross.api.models;


import com.fulmicotone.aws.cluster.cross.api.models.fields.*;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSResource;

import java.util.List;

public class EmrCluster extends AWSResource {

    private EmrCluster(){}

    public MyString subnetId=new MyString();
    public MyString amiVersion=new MyString();
    public MyString masterInstanceType=new MyString();
    public MyString coreInstanceType=new MyString();
    public MyString masterInstanceBidPrice=new MyString();
    public MyInteger coreInstanceCount=new MyInteger();
    public MyString region=new MyString();
    public MyString terminateAfter=new MyString();
    public MyLocalDateTime endDateTime=new MyLocalDateTime();;
    public MyInteger occurrences=new MyInteger() ;
    public MyActionOnResourceFailure actionOnResourceFailure=new MyActionOnResourceFailure();
    public MyString additionalMasterSecurityGroupIds=new MyString();
    public MyString additionalSlaveSecurityGroupIds=new MyString();
    public MyString applications=new MyString();
    public MyString attemptStatus=new MyString();
    public MyString keyPair=new MyString();
    public MyPeriod attemptTimeout=new MyPeriod();
    public MyString availabilityZone=new MyString();
    public MyString bootstrapAction=new MyString();
    public MyString coreInstanceBidPrice=new MyString();
    public List<EmrConfiguration> configuration;
    public List<EmrConfiguration> coreGroupConfiguration;
    public List<EmrConfiguration> coreEbsConfiguration;
    public MyInteger maximumRetries=new MyInteger();
    public MyString releaseLabel=new MyString();
    public  MyString resourceRole=new MyString();
    public  MyString role=new MyString();;
    public  MyString pipelineLogUri=new MyString();;
    public  MyFailureAndRerunMode failureAndRerunMode=new MyFailureAndRerunMode();

}
