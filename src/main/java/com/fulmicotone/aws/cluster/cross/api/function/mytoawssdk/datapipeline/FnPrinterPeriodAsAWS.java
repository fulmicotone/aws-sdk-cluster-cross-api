package com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline;

import com.fulmicotone.aws.cluster.cross.api.exception.InvalidPeriodExeception;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

public class FnPrinterPeriodAsAWS implements Function<Duration, String> {

  private  final static String Y="years";
    private final static String M="months";
    private final static String D="days";
    private final static String W="weeks";
    private final static String HOURS="hours";
    private  final static String MINUTES="minutes";

    @Override
    public String apply(Duration d) {

        LocalDateTime now = Instant.now().atOffset(ZoneOffset.UTC).toLocalDateTime();

        LocalDateTime nowplus=now.plus(d);

        long val=ChronoUnit.YEARS.between(now,nowplus );

        if(val>0){ if(val>3){
            throw new InvalidPeriodExeception("the maximum period is 3 years."); }
        return val+ " "+Y; }
        val=ChronoUnit.MONTHS.between(now,nowplus );
        if(val>0){ return val+ " "+M; }
        val=ChronoUnit.WEEKS.between(now,nowplus );
        if(val==4){ return val/4+ " "+M; }
        if(val>0){ return val+ " "+W; }
        val=ChronoUnit.DAYS.between(now,nowplus );
        if(val>0){ return val+ " "+D; }
        val=ChronoUnit.HOURS.between(now,nowplus );
        if(val>0){ return val+ " "+HOURS; }
        val=ChronoUnit.MINUTES.between(now,nowplus );
        if(val>15){ return val+ " "+MINUTES; }

       throw new InvalidPeriodExeception("The minimum period is 15 minutes");
    }



}
