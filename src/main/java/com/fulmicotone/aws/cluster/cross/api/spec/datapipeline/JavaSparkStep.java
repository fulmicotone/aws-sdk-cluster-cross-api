package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline;

import com.amazonaws.services.elasticmapreduce.model.ActionOnFailure;
import com.amazonaws.services.elasticmapreduce.model.HadoopJarStepConfig;
import com.amazonaws.services.elasticmapreduce.model.StepConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaSparkStep {

    final static String default_jar="command-runner.jar";
    final static String default_spark="spark-submit";

    final static String default_master="--master";
    final static String default_deploy_mode="--deploy-mode";
    final static String default_class="--class";
    final static String default_config="--conf";
    final static String default_repo= "--packages";

    final  SparkDeployMode deployMode;
    final String masterUrl;
    final  String mainClass;
    final String jarPath;
    final  List<String> args;
    final  List<String> configs;
    final  List<String> repo;

    private JavaSparkStep(
                        String masterUrl,
                        String jarPath,
                         String mainClass,
                         SparkDeployMode mode,
                            List<String> args,
                            List<String>  configs,
                        List<String> repo) {

            this.masterUrl=masterUrl;
            this.mainClass=mainClass;
            this.jarPath=jarPath;
            this.deployMode=mode;
            this.args=args;
            this.configs=configs;
            this.repo=repo;
    }

    @Override
    public String toString() {

        List<String> meta =
                Stream.of( default_jar,
                default_spark,
                default_master,
                masterUrl,
                default_deploy_mode,
                deployMode.name()).collect(Collectors.toList());

        if(repo.size()>0){
            meta.add(default_repo);
            meta.add(repo.stream().collect(Collectors.joining(",")));
        }
        configs.stream().forEach(configValue-> {
            meta.add(default_config);
            meta.add(configValue);
        });

        meta.add(default_class);
        meta.add(mainClass);
        meta.add(jarPath);

       return String.join(",", new ArrayList(meta){{addAll(args);}});
       }


       public StepConfig toEmrStepConfig(String name, ActionOnFailure action){

           List<String> meta = new ArrayList<>(Arrays.asList(
                   default_spark,
                   default_master, masterUrl,
                   default_deploy_mode, deployMode.name()));

           configs.stream().forEach(configValue->{
               meta.add(default_config);
               meta.add(configValue);
           });


           if(repo.size()>0){
               meta.add(default_repo);
               meta.add(repo.stream().collect(Collectors.joining(",")));
           }

           meta.add(default_class);
           meta.add(mainClass);
           meta.add(jarPath);

           HadoopJarStepConfig sparkStepConf = new HadoopJarStepConfig()
                   .withJar(default_jar)
                   .withArgs(new ArrayList(meta) {{addAll(args);}});

           return new StepConfig()
                   .withActionOnFailure(action)
                   .withName(name)
                   .withHadoopJarStep(sparkStepConf);
       }



       public static class JavaSparkBuilder{

        SparkDeployMode deployMode;
        String masterUrl;
        String mainClass;
        String jarPath;
        List<String> args=new ArrayList<>();
        List<String> configs=new ArrayList<>();
        List<String> repo=new ArrayList<>();

           public JavaSparkBuilder withMasterUrl(String masterUrl, SparkDeployMode deploymentMode){
               this.masterUrl=masterUrl;
               this.deployMode=deploymentMode;
               return this;
           }

           public JavaSparkBuilder withJar(String jarPath){this.jarPath=jarPath; return this;}

           public JavaSparkBuilder withMainClass(String mainClass){this.mainClass=mainClass; return this;}

           public JavaSparkBuilder addArgs(String argkey,String value){ return this.addArgs(argkey).addArgs(value); }

           public JavaSparkBuilder addArgs(String argValue){ this.args.add(argValue); return this;}

           public JavaSparkBuilder addConfigs(String confKey,String confValue){
               this.configs.add(String.join("=", confKey,confValue));
               return this;
           }


           public JavaSparkBuilder addRepo(String repository){
               this.repo.add(repository);
               return this;
           }

           public JavaSparkBuilder addArgsEntry(String argkey,String value){ return this
                   .addArgs(String.join("=",argkey,value)); }


           public void clearArgs(){ this.args.clear();}

           public JavaSparkStep create(){


                return new JavaSparkStep(masterUrl,
                        jarPath,
                        mainClass,
                        deployMode,
                        args,configs,repo);

           }

       }


}
