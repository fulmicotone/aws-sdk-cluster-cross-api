package com.fulmicotone.aws.cluster.cross.api.exception;

public class MyEMRIsIdleAlarmCreationException extends RuntimeException{


    public MyEMRIsIdleAlarmCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyEMRIsIdleAlarmCreationException(String message) {
        super(message);
    }
}
