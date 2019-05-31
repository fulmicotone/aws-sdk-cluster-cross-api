package com.fulmicotone.aws.cluster.cross.api.builder;


import com.fulmicotone.aws.cluster.cross.api.models.Schedule;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyDuration;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyInteger;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyLocalDateTime;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;

public class MySchedule {


    public static MyScheduleBuilder newCron (){ return new MyScheduleBuilder(); }



    public static class MyScheduleBuilder {

        //required Required Group
        private MyDuration period=new MyDuration();
        private MyLocalDateTime startDateTime=new MyLocalDateTime();
        //optional Optional Fields
        private MyLocalDateTime endDateTime=new MyLocalDateTime();;
        private MyInteger occurrences=new MyInteger() ;
        private String id;
        private String name;


        private MyScheduleBuilder() { }


        public MyScheduleBuilder withTag(String id, String name){
            this.id=id;
            this.name=name;
            return this;
        }


        public  MyScheduleBuilder withPeriod(MyDuration duration){

            this.period=duration;
            return this;
        }

        public MyScheduleBuilder withTimeLimits(MyLocalDateTime start,
                                                MyLocalDateTime end){

            this.startDateTime=start;
            this.endDateTime=end;
            return this;
        }

        public MyScheduleBuilder withOccurrences(MyInteger occurrences){

            this.occurrences=occurrences;
            return this;

        }


        public  Schedule creation(){


            Schedule schedule = MyPipelineObjectFactory
                    .newObject(PipelineObjectsClasses.Schedule,
                    this.id, this.name);

            schedule. period=new MyDuration();
            schedule. startDateTime=new MyLocalDateTime();
            //optional Optional Fields
            schedule. endDateTime=new MyLocalDateTime();;
            schedule. occurrences=new MyInteger() ;


            return schedule;

        }

    }

}
