package com.fulmicotone.aws.cluster.cross.api;


import com.fulmicotone.aws.cluster.cross.api.function.my.FnExtractConfigurationsFromResource;
import com.fulmicotone.aws.cluster.cross.api.function.my.FnExtractPropertiesFromConfiguration;
import com.fulmicotone.aws.cluster.cross.api.function.my.FnGetUniqueActionsFromActivities;
import com.fulmicotone.aws.cluster.cross.api.function.my.FnGetUniqueResourcesFromActivities;
import com.fulmicotone.aws.cluster.cross.api.models.Default;
import com.fulmicotone.aws.cluster.cross.api.models.generic.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class MyPipeline {

    private static Logger logger =LoggerFactory.getLogger(MyPipeline.class);

    public final Default aDefault;
    public final List<AWSActivity> activityList;
    public final List<AWSDatanode> datanodeList;
    public final List<AWSResource> resources;
    public final List<AWSConfiguration> configurations;
    public final List<AWSProperty> properties;
    public final List<AWSAction> actions;

    private MyPipeline(Default aDefault,
                       List<AWSActivity> activityList,
                       List<AWSDatanode> datanodeList,
                       List<AWSResource> resources,
                       List<AWSConfiguration> configurations,
                       List<AWSProperty> properties,
                       List<AWSAction> actions) {
        this.aDefault = aDefault;
        this.activityList = activityList;
        this.datanodeList = datanodeList;
        this.resources =resources;
        this.configurations=configurations;
        this.properties=properties;
        this.actions=actions;
    }


    public static Builder newOne(Default aDefault){ return new Builder(aDefault); }

    /**
     * this is under construction i'm developing only what i need.
     * Data Nodes     x
     * Activities     x
     * Resources
     * Preconditions
     * Databases
     * Data Formats
     * Actions
     * Schedule
     * Utilities
     */
   public static class Builder{

       private final static FnGetUniqueResourcesFromActivities getResFromActivities = new
               FnGetUniqueResourcesFromActivities();

       private final static FnGetUniqueActionsFromActivities getActionsFromActivities = new FnGetUniqueActionsFromActivities();

       private final static FnExtractConfigurationsFromResource getConfigFromResource=new
               FnExtractConfigurationsFromResource();


        private final static FnExtractPropertiesFromConfiguration getPropertiesFromConfiguration=new
                FnExtractPropertiesFromConfiguration();

       private Default aDefault;
       private Map<String,AWSActivity> activityMap =new HashMap<>();
       private Map<String,AWSDatanode> datanodeList=new HashMap<>();

       public Builder addActivity(AWSActivity activity){
           this.activityMap.put(activity.id.getVal(),activity);
           return this;
       }

       public Builder addDatanode(AWSDatanode datanode){
           this.datanodeList.put(datanode.id.getVal(),datanode);
           return this;
       }

       protected Builder(Default aDefault){ this.aDefault=aDefault; }

       public MyPipeline create(){

           Objects.requireNonNull(aDefault,"impossible to create pipeline without default object!!");

           assert activityMap.values().size()>0;

           List<AWSAction> actions = getActionsFromActivities
                   .apply(new ArrayList<>(activityMap.values()));

           List<AWSResource> resources = getResFromActivities
                   .apply(new ArrayList<>(activityMap.values()));

          List<AWSConfiguration> configurations= new ArrayList<>(resources.stream().map(getConfigFromResource)
                  .flatMap(List::stream).collect(Collectors.toMap(r -> r.id.getVal(), r -> r, (x, y) -> x))
                  .values());


          List<AWSProperty> properties=configurations
                  .stream().map(getPropertiesFromConfiguration)
                  .flatMap(List::stream)
                  .collect(Collectors.toMap(r->r.id.getVal(), r->r ,(x, y)->x))
                  .values().stream().collect(Collectors.toList());

           return new MyPipeline(aDefault,
                   activityMap.values().stream().collect(Collectors.toList()),
                   Optional.ofNullable(datanodeList.values()).orElse(new ArrayList<>())
                           .stream().collect(Collectors.toList()),
                   resources,
                   configurations,
                   properties,
                   actions
                   );
       }

   }
}