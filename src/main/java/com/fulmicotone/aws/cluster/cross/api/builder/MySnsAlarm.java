package com.fulmicotone.aws.cluster.cross.api.builder;

import com.fulmicotone.aws.cluster.cross.api.models.SnsAlarm;
import com.fulmicotone.aws.cluster.cross.api.models.enums.PipelineObjectsClasses;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.MyPipelineObjectFactory;

public class MySnsAlarm {


    private MySnsAlarm(){}

    public static class MySnsAlarmBuilder {


        private MySnsAlarmBuilder() {  }
        public static MySnsAlarmBuilder newOne() { return new MySnsAlarmBuilder(); }

        protected String id;
        protected String name;
        protected MyString message = new MyString();
        protected MyString role = new MyString();
        protected MyString subject = new MyString();
        protected MyString topicArn = new MyString();


        public MySnsAlarmBuilder withTag(String id,String name){
            this.id=id;
            this.name=name;
            return this;
        }


        public MySnsAlarmBuilder withMessage(MyString message){
            this.message=message;
            return this;
        }

        public MySnsAlarmBuilder withRole(MyString role){
            this.role=role;
            return this;
        }

        public MySnsAlarmBuilder withSubject(MyString subject){
            this.subject=subject;
            return this;
        }

        public MySnsAlarmBuilder withTopicArn(MyString topicArn){
            this.topicArn=topicArn;
            return this;
        }



        public SnsAlarm creation(){

            SnsAlarm snsAlarm = MyPipelineObjectFactory.newObject(PipelineObjectsClasses.SnsAlarm, id, name);

            snsAlarm.message = this.message;
            snsAlarm.subject = this.subject;
            snsAlarm.role = this.role;
            snsAlarm.topicArn = this.topicArn;

            return snsAlarm;
        }


    }
}
