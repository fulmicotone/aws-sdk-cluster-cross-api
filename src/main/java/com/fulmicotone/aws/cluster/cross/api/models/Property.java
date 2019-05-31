package com.fulmicotone.aws.cluster.cross.api.models;


import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSProperty;

public class Property  extends AWSProperty {

   private Property(){}

   public MyString key=new MyString();
   public MyString value=new MyString();




}
