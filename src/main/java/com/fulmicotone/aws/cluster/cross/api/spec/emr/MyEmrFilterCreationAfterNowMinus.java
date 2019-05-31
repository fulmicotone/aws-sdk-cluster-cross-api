package com.fulmicotone.aws.cluster.cross.api.spec.emr;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class MyEmrFilterCreationAfterNowMinus extends MyEmrFilterListRequest {
    public MyEmrFilterCreationAfterNowMinus(int amount, ChronoUnit unit) {
        super();
         withCreatedAfter(Date.from(LocalDateTime.now()
                .minus(amount,unit).toInstant(ZoneOffset.UTC)));
    }
}