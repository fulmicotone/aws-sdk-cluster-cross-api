package com.fulmicotone.aws.cluster.cross.api.exception;

public class MyEMRResourceCreationException extends RuntimeException{


    public MyEMRResourceCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyEMRResourceCreationException(String message) {
        super(message);
    }
}
