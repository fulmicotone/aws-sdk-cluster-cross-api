package com.fulmicotone.aws.cluster.cross.api.exception;

public class InvalidPeriodExeception extends RuntimeException{


    public InvalidPeriodExeception(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPeriodExeception(String message) {
        super(message);
    }
}
