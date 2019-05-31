package com.fulmicotone.aws.cluster.cross.api.exception;

public class MyEmrObservableResourceCreationException extends RuntimeException{


    public MyEmrObservableResourceCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyEmrObservableResourceCreationException(String message) {
        super(message);
    }
}
