package com.fulmicotone.aws.cluster.cross.api.spec.emr.model;

public class MyEMRScaleUpPolicy {


    private final int  maxClusterOpened;
    private final int  maxPendingStep;
    private final MyEMRThresholdBreakBehaviour b;

    public MyEMRScaleUpPolicy(int maxClusterOpened, int maxPendingStep, MyEMRThresholdBreakBehaviour behaviour) {
        this.maxClusterOpened=maxClusterOpened;
        this.maxPendingStep=maxPendingStep;
        this.b=behaviour;
    }

    public MyEMRThresholdBreakBehaviour getBehaviourOnClusterThreadsholdBreak() {
        return b;
    }

    public  boolean pendingStepsBreakThreshold(int pendingStep){ return maxPendingStep!=-1&&pendingStep>=maxPendingStep; }

    public boolean clusterOpenedBreakThreshold(int clusterOpened){ return clusterOpened>=maxClusterOpened; }


    @Override
    public String toString() {
        return String.format(
                "EMR Scale Policy{\"max_opened_cluster:\"%s,\"max_pending_steps:\"%s,\"step_commit_behaviour:\"%s}",
        maxClusterOpened,maxPendingStep,b);
    }

    public static final class Builder {
        private int  maxClusterOpened=1;
        private int  maxPendingStep=-1;
        private MyEMRThresholdBreakBehaviour b=MyEMRThresholdBreakBehaviour.commit_anyway;

        private Builder() {
        }

        public static Builder newOne() { return new Builder(); }

        public Builder withClustersThreashold(int maxClusterOpened) {
            this.maxClusterOpened = maxClusterOpened;
            return this;
        }

        public Builder withPendingStepThreashold(int maxPendingStep) {
            this.maxPendingStep = maxPendingStep;
            return this;
        }

        public Builder withStepCommitBehaviourOnThreasholdBreak(MyEMRThresholdBreakBehaviour b) {
            this.b = b;
            return this;
        }



        public MyEMRScaleUpPolicy build() {

            return new MyEMRScaleUpPolicy(maxClusterOpened,
                    maxPendingStep , b) ;
        }
    }


    /**
     *
     * @return a standard scale policy unlimited step pending step and 1 cluster
     */
    public static MyEMRScaleUpPolicy STANDARD_SCALE_POLICY(){

        return Builder.newOne().build();
    }
}
