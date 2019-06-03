package com.fulmicotone.aws.cluster.cross.api.spec.emr.business.getter.filters;

import com.fulmicotone.aws.cluster.cross.api.spec.emr.business.getter.filters.generic.MyEMRClusterFilterListRequest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class MyEMRClusterFilterCreationAfterNowMinus extends MyEMRClusterFilterListRequest {
    public MyEMRClusterFilterCreationAfterNowMinus(int amount, ChronoUnit unit) {
        super();
         withCreatedAfter(Date.from(LocalDateTime.now()
                .minus(amount,unit).toInstant(ZoneOffset.UTC)));
    }
}