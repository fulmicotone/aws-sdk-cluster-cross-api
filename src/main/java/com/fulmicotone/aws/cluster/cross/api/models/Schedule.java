package com.fulmicotone.aws.cluster.cross.api.models;


import com.fulmicotone.aws.cluster.cross.api.models.fields.MyDuration;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyInteger;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyLocalDateTime;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSSchedule;

public class Schedule extends AWSSchedule {

    private Schedule(){}
    //required Required Group
    public MyDuration period=new MyDuration();
    public MyLocalDateTime startDateTime=new MyLocalDateTime();
    //optional Optional Fields
    public MyLocalDateTime endDateTime=new MyLocalDateTime();;
    public MyInteger occurrences=new MyInteger() ;

}
