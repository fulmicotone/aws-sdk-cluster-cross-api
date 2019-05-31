package com.fulmicotone.aws.cluster.cross.api.builder;


import com.fulmicotone.aws.cluster.cross.api.exception.MyActivityCreationException;
import com.fulmicotone.aws.cluster.cross.api.models.EmrActivity;
import com.fulmicotone.aws.cluster.cross.api.models.Schedule;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.models.enums.ScheduleType;
import com.fulmicotone.aws.cluster.cross.api.models.fields.*;
import com.fulmicotone.aws.cluster.cross.api.models.generic.*;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;

import java.util.Objects;

/**
 * todo  complete
 */
public class MyActivity {


    private MyActivity(){}


    //todo
    public  static AWSActivity newEC2Resource() { return null; }


    public static EmrActivityBuilder newEMR() { return new EmrActivityBuilder(); }


    /**
     * todo all fields are public for now
     */
    public static class EmrActivityBuilder {


        private EmrActivityBuilder() {  }

        protected String id;
        protected String name;
        protected MyString workerGroup = new MyString();
        protected MyString attemptStatus = new MyString();
        protected MyDuration attemptTimeout = new MyDuration();
        protected AWSActivity dependsOn;
        protected AWSDatanode input;
        protected MyPeriod lateAfterTimeout = new MyPeriod();
        protected MyInteger maxActiveInstances = new MyInteger();
        protected MyInteger maximumRetries = new MyInteger();
        protected AWSAction onFail;
        protected AWSAction onLateAction;
        protected AWSAction onSuccess;
        protected AWSDatanode output;
        protected MyPipelineObject parent;
        protected MyString postStepCommand = new MyString();
        protected AWSAction precondition;
        protected MyString preStepCommand = new MyString();
        protected MyDuration reportProgressTimeout = new MyDuration();
        protected MyBoolean resizeClusterBeforeRunning = new MyBoolean();
        protected MyInteger resizeClusterMaxInstances = new MyInteger();
        protected MyDuration retryDelay = new MyDuration();
        protected MyScheduleType scheduleType = new MyScheduleType();
        protected Schedule schedule;
        protected MyString step = new MyString();
        protected AWSResource runsOn;

        public EmrActivityBuilder withTag(String id,String name){
            this.id=id;
            this.name=name;
            return this;
        }


        public EmrActivityBuilder withSchedule(Schedule schedule){
            this.schedule=schedule;
            this.scheduleType.setVal(ScheduleType.cron);
            return this;
        }


        public EmrActivityBuilder withRunOn(AWSResource awsResource){
            this.runsOn=awsResource;
            return this;
        }

        public EmrActivityBuilder withOnSuccess(AWSAction onSuccess){
            this.onSuccess=onSuccess;
            return this;
        }

        public EmrActivityBuilder withRunOnWorkergroup(MyString workerGroup){
            this.workerGroup=workerGroup;
            return this;
        }

        public EmrActivityBuilder withStep(MyString step){
            this.step=step;
            return this;
        }

        public EmrActivity creation(){


            if(runsOn==null&&workerGroup.isNull()){
                throw  new MyActivityCreationException("runsOn resource or workerGroup  are required!!");
            }
            Objects.requireNonNull(step.isNull()?null:step, "step is required");

            EmrActivity emrActivity = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.EmrActivity, id, name);
            emrActivity.runsOn=runsOn;//x
            emrActivity.workerGroup = workerGroup;
            emrActivity.attemptStatus = attemptStatus;
            emrActivity.attemptTimeout = attemptTimeout;
            emrActivity.dependsOn=dependsOn;
            emrActivity.input=input;
            emrActivity.lateAfterTimeout = lateAfterTimeout;
            emrActivity.maxActiveInstances = maxActiveInstances;
            emrActivity.maximumRetries = maximumRetries;
            emrActivity.onFail=onFail;
            emrActivity.onLateAction=onLateAction;
            emrActivity.onSuccess=onSuccess;
            emrActivity.output=output;
            emrActivity.parent=parent;
            emrActivity.postStepCommand = postStepCommand;
            emrActivity.precondition=precondition;
            emrActivity.preStepCommand = preStepCommand;
            emrActivity.reportProgressTimeout =reportProgressTimeout;
            emrActivity.resizeClusterBeforeRunning = resizeClusterBeforeRunning;
            emrActivity.resizeClusterMaxInstances = resizeClusterMaxInstances;
            emrActivity.retryDelay = retryDelay;
            emrActivity.scheduleType = scheduleType;
            emrActivity. schedule=schedule;//x
            emrActivity. step = step;//x
            return emrActivity;
        }


    }

}
