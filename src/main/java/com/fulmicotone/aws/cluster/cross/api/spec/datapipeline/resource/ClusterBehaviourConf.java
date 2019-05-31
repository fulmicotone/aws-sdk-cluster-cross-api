package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource;

import com.fulmicotone.aws.cluster.cross.api.models.fields.*;

public class ClusterBehaviourConf {


    public MyString terminateAfter;
    public MyLocalDateTime endDateTime;
    public MyInteger occurrences;
    public MyString attemptStatus;
    public MyActionOnResourceFailure actionOnResourceFailure;
    public MyPeriod attemptTimeout;
    public MyInteger maximumRetries;
    public MyString bootstrapAction;
    public MyFailureAndRerunMode failureAndRerunMode;

    public ClusterBehaviourConf(){}
    public ClusterBehaviourConf(MyString terminateAfter, MyLocalDateTime endDateTime, MyInteger occurrences,
                                MyString attemptStatus, MyActionOnResourceFailure actionOnResourceFailure,
                                MyPeriod attemptTimeout, MyInteger maximumRetries, MyString bootstrapAction,
                                MyFailureAndRerunMode failureAndRerunMode) {
        this.terminateAfter = terminateAfter;
        this.endDateTime = endDateTime;
        this.occurrences = occurrences;
        this.attemptStatus = attemptStatus;
        this.actionOnResourceFailure = actionOnResourceFailure;
        this.attemptTimeout = attemptTimeout;
        this.maximumRetries = maximumRetries;
        this.bootstrapAction = bootstrapAction;
        this.failureAndRerunMode = failureAndRerunMode;
    }


    public static final class ClusterBehaviourConfBuilder {
        public MyString terminateAfter;
        public MyLocalDateTime endDateTime;
        public MyInteger occurrences;
        public MyString attemptStatus;
        public MyActionOnResourceFailure actionOnResourceFailure;
        public MyPeriod attemptTimeout;
        public MyInteger maximumRetries;
        public MyString bootstrapAction;
        public MyFailureAndRerunMode failureAndRerunMode;

        private ClusterBehaviourConfBuilder() {
        }

        public static ClusterBehaviourConfBuilder newOne() {
            return new ClusterBehaviourConfBuilder();
        }

        public ClusterBehaviourConfBuilder withTerminateAfter(MyString terminateAfter) {
            this.terminateAfter = terminateAfter;
            return this;
        }

        public ClusterBehaviourConfBuilder withEndDateTime(MyLocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
            return this;
        }

        public ClusterBehaviourConfBuilder withOccurrences(MyInteger occurrences) {
            this.occurrences = occurrences;
            return this;
        }

        public ClusterBehaviourConfBuilder withAttemptStatus(MyString attemptStatus) {
            this.attemptStatus = attemptStatus;
            return this;
        }

        public ClusterBehaviourConfBuilder withActionOnResourceFailure(MyActionOnResourceFailure actionOnResourceFailure) {
            this.actionOnResourceFailure = actionOnResourceFailure;
            return this;
        }

        public ClusterBehaviourConfBuilder withAttemptTimeout(MyPeriod attemptTimeout) {
            this.attemptTimeout = attemptTimeout;
            return this;
        }

        public ClusterBehaviourConfBuilder withMaximumRetries(MyInteger maximumRetries) {
            this.maximumRetries = maximumRetries;
            return this;
        }

        public ClusterBehaviourConfBuilder withBootstrapAction(MyString bootstrapAction) {
            this.bootstrapAction = bootstrapAction;
            return this;
        }

        public ClusterBehaviourConfBuilder withFailureAndRerunMode(MyFailureAndRerunMode failureAndRerunMode) {
            this.failureAndRerunMode = failureAndRerunMode;
            return this;
        }

        public ClusterBehaviourConf build() {
            return new ClusterBehaviourConf(terminateAfter,
                    endDateTime,
                    occurrences,
                    attemptStatus,
                    actionOnResourceFailure,
                    attemptTimeout, maximumRetries, bootstrapAction, failureAndRerunMode);
        }
    }
}
