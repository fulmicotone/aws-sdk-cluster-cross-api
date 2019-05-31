package com.fulmicotone.aws.cluster.cross.api.exception;

public class MyAWSRetryableException extends RuntimeException{


    public MyAWSRetryableException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyAWSRetryableException(String message) {
        super(message);
    }
}
