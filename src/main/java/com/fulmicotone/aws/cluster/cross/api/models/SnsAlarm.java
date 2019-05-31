package com.fulmicotone.aws.cluster.cross.api.models;

import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSAction;

public class SnsAlarm extends AWSAction {

    private SnsAlarm() {}
    public MyString message = new MyString();
    public MyString role = new MyString();
    public MyString subject = new MyString();
    public MyString topicArn = new MyString();
}
