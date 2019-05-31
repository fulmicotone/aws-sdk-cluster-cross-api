package com.fulmicotone.aws.cluster.cross.api.models.generic;

public class AWSActivity  extends
        MyPipelineObject implements
        IAWSActivity{

    public AWSResource runsOn;
    public AWSAction onFail;
    public AWSAction onLateAction;
    public AWSAction onSuccess;
}
