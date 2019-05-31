package com.fulmicotone.aws.cluster.cross.api.models.fields;

import com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline.FnPrinterPeriodAsAWS;
import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipeLineObjectField;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

public class MyDuration extends MyPipeLineObjectField<Duration> {

    public void setVal(long amount, TemporalUnit unit) {

        if(unit!=ChronoUnit.MONTHS&&unit!=ChronoUnit.WEEKS&& unit!=ChronoUnit.YEARS){
            super.setVal(Duration.of(amount, unit));
        }else{

            LocalDateTime ldt = Instant.now().atOffset(ZoneOffset.UTC).toLocalDateTime();
            LocalDateTime ldtPlus = ldt.plus(amount, unit);
           super.setVal(Duration.between(ldt,ldtPlus ));
        }
    }




    @Override
    public String getVal() {

        return Optional.ofNullable(val).isPresent()?
                new FnPrinterPeriodAsAWS().apply(val) : "#{"+Optional.of(placeHolder).get()+"}";
    }
}

