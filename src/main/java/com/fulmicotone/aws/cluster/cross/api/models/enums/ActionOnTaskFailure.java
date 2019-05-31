package com.fulmicotone.aws.cluster.cross.api.models.enums;

public enum ActionOnTaskFailure {

    TERMINATE("terminate"),
    CONTINUE("continue");

    private final String val;

    ActionOnTaskFailure(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }


    @Override
    public String toString() {
        return val;
    }
}
