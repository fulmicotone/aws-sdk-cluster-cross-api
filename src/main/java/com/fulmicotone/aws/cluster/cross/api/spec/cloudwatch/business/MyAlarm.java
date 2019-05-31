package com.fulmicotone.aws.cluster.cross.api.spec.cloudwatch.business;

import com.amazonaws.services.cloudwatch.model.PutMetricAlarmRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//generic cloudWatch alarm
public class MyAlarm extends PutMetricAlarmRequest {

    private final static String alarm_suffix="-ALARM";
    protected Logger log= LoggerFactory.getLogger(MyAlarm.class);

    protected MyAlarm(String alarmName, String nameSpace, String description) {
        super();
        withAlarmName(String.join("-",alarmName,alarm_suffix))
                .withNamespace(nameSpace)
                .withAlarmDescription(String.format(description));
    }
}