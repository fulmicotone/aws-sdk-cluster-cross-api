package com.fulmicotone.aws.cluster.cross.api.models.fields;

import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipeLineObjectField;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;

public class MyPeriod extends MyPipeLineObjectField<Period> {


    public void setVal(long amount, TemporalUnit unit) {
        Instant n = Instant.now();
        Instant nplus = n.plus(Duration.of(amount, unit));
        super.setVal(Period.between(n.atOffset(ZoneOffset.UTC).toLocalDate(), nplus
                .atOffset(ZoneOffset.UTC).toLocalDate()));
    }
}

