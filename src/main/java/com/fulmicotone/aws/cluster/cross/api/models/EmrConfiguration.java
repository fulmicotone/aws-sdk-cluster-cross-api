package com.fulmicotone.aws.cluster.cross.api.models;


import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;
import com.fulmicotone.aws.cluster.cross.api.models.generic.AWSConfiguration;

import java.util.ArrayList;
import java.util.List;

public class EmrConfiguration extends AWSConfiguration {

   private EmrConfiguration(){}

   public MyString classification=new MyString();
   public List<EmrConfiguration> configuration =new ArrayList<>();
   public List<Property> property =new ArrayList<>();


}
