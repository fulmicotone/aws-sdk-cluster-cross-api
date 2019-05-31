package com.fulmicotone.aws.cluster.cross.api.models.enums;

import com.fulmicotone.aws.cluster.cross.api.models.Default;
import com.fulmicotone.aws.cluster.cross.api.models.EmrConfiguration;
import com.fulmicotone.aws.cluster.cross.api.models.Schedule;
import com.fulmicotone.aws.cluster.cross.api.models.generic.MyPipelineObject;

public enum PipelineObjectsClasses {

    Default(Default.class),
    EmrActivity(com.fulmicotone.aws.cluster.cross.api.models.EmrActivity.class),
    Schedule(Schedule .class),
    Configuration(EmrConfiguration.class),
    Property(com.fulmicotone.aws.cluster.cross.api.models.Property.class),
    EmrCluster(com.fulmicotone.aws.cluster.cross.api.models.EmrCluster.class),
    SnsAlarm(com.fulmicotone.aws.cluster.cross.api.models.SnsAlarm.class);

    private final Class val;

    PipelineObjectsClasses(Class val) {
        this.val = val;
    }

    public Class<MyPipelineObject> getVal() { return val; }


}
