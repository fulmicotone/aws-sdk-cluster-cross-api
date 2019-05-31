package com.fulmicotone.aws.cluster.cross.api.spec.cloudwatch.business;

import com.amazonaws.services.cloudwatch.model.ComparisonOperator;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.cloudwatch.model.Statistic;
import com.fulmicotone.aws.cluster.cross.api.spec.cloudwatch.enumerator.Metrics;

import java.util.Arrays;
import java.util.List;

public class MyEMRIsIndleAlarm extends MyEMRAlarm {

     private final static String description="Alarm when EMR cluster is Idle for %s %s";


    public MyEMRIsIndleAlarm(String clusterId,
                             String... snsTopic) {

        this(clusterId,60*3,2,2,snsTopic);

    }

    public MyEMRIsIndleAlarm(String clusterId,
                             int periodAmount,
                             int evaluationPeriods,
                             int datapoints,
                             String... snsTopic) {
        super(clusterId,
                String.format(description,periodAmount,StandardUnit.Count),
                ComparisonOperator.GreaterThanOrEqualToThreshold,
                Metrics.EmrMetrics.IsIdle,
                Statistic.Minimum,
                periodAmount,
                evaluationPeriods,
                datapoints,
                1d);




        List<String> snsTopicList = Arrays.asList(snsTopic);

        withInsufficientDataActions(snsTopicList)
                .withAlarmActions(snsTopicList)
                        .withTreatMissingData("ignore")
                .withActionsEnabled(snsTopic.length>0);

    }
}