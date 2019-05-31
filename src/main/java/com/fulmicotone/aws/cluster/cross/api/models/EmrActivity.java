package com.fulmicotone.aws.cluster.cross.api.models;


import com.fulmicotone.aws.cluster.cross.api.models.fields.*;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSAction;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSActivity;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSDatanode;
import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipelineObject;

/**
 * todo step creator with builder
 */
public class EmrActivity extends AWSActivity {
    private EmrActivity(){}

    public MyString workerGroup=new MyString();
    public MyString attemptStatus=new MyString();
    public MyDuration attemptTimeout=new MyDuration();
    public AWSActivity  dependsOn;
    public AWSDatanode input;

    public MyPeriod lateAfterTimeout=new MyPeriod();
    public MyInteger maxActiveInstances=new MyInteger();
    public MyInteger maximumRetries=new MyInteger();
    public AWSDatanode output;
    public MyPipelineObject parent;
    public MyString  postStepCommand=new MyString();
    public AWSAction precondition;
    public MyString preStepCommand=new MyString();
    public MyDuration        reportProgressTimeout=new MyDuration();
    public MyBoolean resizeClusterBeforeRunning=new MyBoolean();
    public MyInteger        resizeClusterMaxInstances=new MyInteger();
    public MyDuration retryDelay=new MyDuration();
    public MyScheduleType scheduleType=new MyScheduleType();
    public Schedule schedule;
    public MyString step=new MyString();

}
