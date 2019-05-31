package com.fulmicotone.aws.cluster.cross.api.models;


import com.fulmicotone.aws.cluster.cross.api.models.fields.MyFailureAndRerunMode;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyScheduleType;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipelineObject;

public class Default extends MyPipelineObject {

    private Default(){}

    public Schedule schedule;
    public MyScheduleType scheduleType=new MyScheduleType();
    public MyString resourceRole=new MyString();
    public  MyString role=new MyString();;
    public  MyString pipelineLogUri=new MyString();;
    public MyFailureAndRerunMode failureAndRerunMode=new MyFailureAndRerunMode();


}
