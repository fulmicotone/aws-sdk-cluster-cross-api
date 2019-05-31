package com.fulmicotone.aws.cluster.cross.api.spec.cloudwatch.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class FnCloudWatchPeriodCheck implements Function<Integer,Integer> {

    Logger log= LoggerFactory.getLogger(FnCloudWatchPeriodCheck.class);

    @Override
    public Integer apply(Integer periodAmount) {
        if(periodAmount<10){
            log.warn("period must be greater than 10. In details 10 or multiple of 30");
            periodAmount=10;
        }else if(periodAmount>10){
            if(periodAmount<30){
                periodAmount=30;
                log.warn("period must be greater than 10. In details 10 or multiple of 30. Period set to 30");
            } else if(periodAmount>30 ) {
                periodAmount= Math.round(periodAmount/30) * 30;
                log.warn("period must be greater than 10. In details 10 or multiple of 30. Period set to "+periodAmount);
            }
        }
        return periodAmount;
    }
}
