package com.fulmicotone.aws.cluster.cross.api.exception;

public class MyActivityCreationException extends RuntimeException{


    public MyActivityCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyActivityCreationException(String message) {
        super(message);
    }
}
