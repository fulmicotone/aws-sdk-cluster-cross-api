package com.fulmicotone.aws.cluster.cross.api.spec.cloudwatch.business;

import com.amazonaws.services.cloudwatch.model.ComparisonOperator;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.Statistic;
import com.fulmicotone.aws.cluster.cross.api.spec.cloudwatch.enumerator.Metrics;
import com.fulmicotone.aws.cluster.cross.api.spec.cloudwatch.enumerator.NameSpaces;
import com.fulmicotone.aws.cluster.cross.api.spec.cloudwatch.function.FnCloudWatchPeriodCheck;


/**
 * generic Emr alarm
 */
public class MyEMRAlarm extends MyAlarm {


    private final ComparisonOperator comparisonOperator;
    private final float periodAmount;
    private final int evaluationPeriods;
    private final Double threshold;
    private final int dataPoints;


    protected MyEMRAlarm(
                      String clusterId,
                      String description,
                      ComparisonOperator comparisonOperator,
                      Metrics.EmrMetrics metric,
                      Statistic statistic,
                      int periodAmount,
                      int evaluationPeriods,//n
                      int dataPoints,//m
                      Double threshold

                      ) {
        super(clusterId, NameSpaces.ElasticMapReduce.getVal(),description);

        this.comparisonOperator=comparisonOperator;
        this.periodAmount=periodAmount;
        this.evaluationPeriods=evaluationPeriods;
        this.threshold=threshold;
        this.dataPoints=dataPoints;

        periodAmount=new FnCloudWatchPeriodCheck().apply(periodAmount);

        if(dataPoints>evaluationPeriods){
            log.warn("Alarm M out N. N must be less or equals M. Set to :"+evaluationPeriods);
        }
                withComparisonOperator(comparisonOperator)
                .withEvaluationPeriods(evaluationPeriods)
                .withNamespace(NameSpaces.ElasticMapReduce.getVal())
                .withPeriod(periodAmount)
                .withMetricName(metric.getVal())
                 .withStatistic(statistic)
                .withThreshold(threshold)
                .withAlarmDescription(description)
                .withDatapointsToAlarm(dataPoints)
                .withDimensions( new Dimension()
                        .withName("JobFlowId")
                        .withValue(clusterId));
    }

}