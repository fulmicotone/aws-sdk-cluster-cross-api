package com.fulmicotone.aws.cluster.cross.api.builder;


import com.fulmicotone.aws.cluster.cross.api.models.Default;
import com.fulmicotone.aws.cluster.cross.api.models.Schedule;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.models.enums.ScheduleType;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyFailureAndRerunMode;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyScheduleType;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;

import java.util.Objects;

public class MyDefault {


    private final static String default_id="Default";

    public static MyDefaultBuilder newOne (){ return new MyDefaultBuilder().withTag(default_id, default_id); }

    public static class MyDefaultBuilder {

        private MyDefaultBuilder(){  this.scheduleType.setVal(ScheduleType.ondemand); }

        private Schedule schedule;
        private MyScheduleType  scheduleType=new MyScheduleType();
        private  MyString resourceRole=new MyString();
        private  MyString role=new MyString();;
        private  MyString pipelineLogUri=new MyString();;
        private  MyFailureAndRerunMode failureAndRerunMode=new MyFailureAndRerunMode();
        private String id;
        private String name;


        public MyDefaultBuilder withTag(String id, String name){
            this.id=id;
            this.name=name;
            return this;
        }


        public MyDefaultBuilder withSchedule(Schedule schedule){
            this.schedule=schedule;
            this.scheduleType.setVal(ScheduleType.cron);
            return this;
        }


        private MyDefaultBuilder withResourceRole(MyString resourceRole){
            this.resourceRole=resourceRole;
            return this;
        }
        public MyDefaultBuilder withRoles(MyString role,MyString resourceRole){

            withRole(role);
            withResourceRole(resourceRole);
            return this;
        }

        private MyDefaultBuilder withRole(MyString role){
            this.role=role;
            return this;
        }

        public MyDefaultBuilder withLogUri(MyString pipelineLogUri){
            this.pipelineLogUri=pipelineLogUri;
            return this;
        }

        public MyDefaultBuilder withFailureAndRerunMode(MyFailureAndRerunMode failureAndRerunMode){
            this.failureAndRerunMode=failureAndRerunMode;
            return this;
        }



        public Default creation(){

            Objects.requireNonNull(resourceRole, "runsOn resource is required");
            Objects.requireNonNull(role, "step is required");
            Default aDefault = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.Default, id, name);

            aDefault.scheduleType=this.scheduleType;
            aDefault.schedule=this.schedule;
            aDefault.failureAndRerunMode=this.failureAndRerunMode;
            aDefault.resourceRole=this.resourceRole;
            aDefault.role=this.role;
            aDefault.pipelineLogUri=this.pipelineLogUri;
            return aDefault;
        }

    }

}
