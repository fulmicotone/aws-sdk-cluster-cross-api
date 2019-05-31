package com.fulmicotone.aws.cluster.cross.api.exception;

public class MyResourceGetException extends RuntimeException{


    public MyResourceGetException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyResourceGetException(String message) {
        super(message);
    }
}
