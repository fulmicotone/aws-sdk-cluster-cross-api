package com.fulmicotone.aws.cluster.cross.api.spec.emr;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticmapreduce.model.StepConfig;
import com.amazonaws.services.elasticmapreduce.util.StepFactory;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TaskRunnerStep extends StepConfig {


       public TaskRunnerStep(String name, String script, List<String> args) {
              super(name,new StepFactory()
                      .newScriptRunnerStep(script,args.toArray(new String[]{})));
       }

       public static class TaskRunnerStepBuilderBase {

              enum keys {
                     endpoint,
                     workerGroup,
                     region,
                     logUri,
                     taskRunnerId,
                     zipFile,
                     mysqlFile,
                     hiveCsvSerdeFile,
                     proxyHost,
                     proxyPort,
                     username,
                     password,
                     windowsDomain,
                     windowsWorkgroup,
                     releaseLabel,
                     jdbcDriverS3Path,
                     s3NoProxy
              }

              protected final static String script_basic_URI="s3://datapipeline-{region}/{region}/bootstrap-actions/latest/TaskRunner/install-remote-runner-v2";
              protected final static String service_URI="https://datapipeline.{region}.amazonaws.com";
              protected final static String zip_file_URI="http://datapipeline-{region}.s3.amazonaws.com/{region}/software/latest/TaskRunner/TaskRunner-1.0.zip";
              protected final static String my_sql_file_URI="http://datapipeline-{region}.s3.amazonaws.com/{region}/software/latest/TaskRunner/mysql-connector-java-bin.jar";
              protected final static String hive_csv_serde_file_URI="http://datapipeline-{region}.s3.amazonaws.com/{region}/software/latest/TaskRunner/csv-serde.jar";
              protected final static String jdbc_driver_s3_path="s3://datapipeline-{region}/{region}/software/latest/TaskRunner/";
              protected String name;
              protected String workerGroup;
              protected Regions region;
              protected String logUri;
              protected String taskRunnerId;
              protected String proxyHost;
              protected String proxyPort;
              protected String username;
              protected String password;
              protected String windowsDomain;
              protected String windowsWorkgroup;
              protected String releaseLabel="emr-5.13.0";
              protected boolean s3NoProxy=false;

              private final  Function<String,String> replace_region=str->str.replace("{region}",region.getName());
              private final BiFunction<keys,String,String> mk_arg=(key, value)->
                      String.join("=","--"+ key.name(),replace_region.apply(Optional.ofNullable(value).orElse("")));

              public TaskRunnerStep build() {

                     Objects.requireNonNull(region,"region is required field!!!!");
                     Objects.requireNonNull(workerGroup,"workergroup is required field!!!!");

                     return new TaskRunnerStep(name,script_basic_URI.replace("{region}", region.getName()),
                             Arrays.asList(
                                     mk_arg.apply(keys.endpoint, service_URI),
                                     mk_arg.apply(keys.workerGroup, workerGroup),
                                     mk_arg.apply(keys.region, region.getName()),
                                     mk_arg.apply(keys.logUri, logUri),
                                     mk_arg.apply(keys.taskRunnerId, taskRunnerId),
                                     mk_arg.apply(keys.zipFile, zip_file_URI),
                                     mk_arg.apply(keys.mysqlFile, my_sql_file_URI),
                                     mk_arg.apply(keys.hiveCsvSerdeFile, hive_csv_serde_file_URI),
                                     mk_arg.apply(keys.proxyHost, proxyHost),
                                     mk_arg.apply(keys.proxyPort, proxyPort),
                                     mk_arg.apply(keys.username, username),
                                     mk_arg.apply(keys.password, password),
                                     mk_arg.apply(keys.windowsDomain, windowsDomain),
                                     mk_arg.apply(keys.windowsWorkgroup, windowsWorkgroup),
                                     mk_arg.apply(keys.releaseLabel, releaseLabel),
                                     mk_arg.apply(keys.jdbcDriverS3Path, jdbc_driver_s3_path),
                                     mk_arg.apply(keys.s3NoProxy, s3NoProxy+"")));
              }
       }

       public static final class TaskRunnerDefault extends TaskRunnerStepBuilderBase {

              private TaskRunnerDefault(String workerGroup, Regions region) {

                     this.name="Install TaskRunner";
                     this.taskRunnerId= UUID.randomUUID().toString();
                     this.region=region;
                     this.workerGroup=workerGroup;

              }

              public static TaskRunnerDefault defaults(String workgroup,Regions region) {
                     return new TaskRunnerDefault(workgroup,region);
              }
       }



       public static final class TaskRunnerStepBuilder extends TaskRunnerStepBuilderBase {



              private TaskRunnerStepBuilder() {
              }

              public static TaskRunnerStepBuilder newOne() {
                     return new TaskRunnerStepBuilder();
              }


              public TaskRunnerStepBuilder withWorkerGroup(String workerGroup) {
                     this.workerGroup = workerGroup;
                     return this;
              }

              public TaskRunnerStepBuilder withRegion(Regions region) {
                     this.region = region;
                     return this;
              }

              public TaskRunnerStepBuilder withLogUri(String logUri) {
                     this.logUri = logUri;
                     return this;
              }

              public TaskRunnerStepBuilder withTaskRunnerId(String taskRunnerId) {
                     this.taskRunnerId = taskRunnerId;
                     return this;
              }

              public TaskRunnerStepBuilder withProxyHost(String proxyHost) {
                     this.proxyHost = proxyHost;
                     return this;
              }

              public TaskRunnerStepBuilder withProxyPort(String proxyPort) {
                     this.proxyPort = proxyPort;
                     return this;
              }

              public TaskRunnerStepBuilder withUsername(String username) {
                     this.username = username;
                     return this;
              }

              public TaskRunnerStepBuilder withPassword(String password) {
                     this.password = password;
                     return this;
              }

              public TaskRunnerStepBuilder withWindowsDomain(String windowsDomain) {
                     this.windowsDomain = windowsDomain;
                     return this;
              }

              public TaskRunnerStepBuilder withWindowsWorkgroup(String windowsWorkgroup) {
                     this.windowsWorkgroup = windowsWorkgroup;
                     return this;
              }

              public TaskRunnerStepBuilder withReleaseLabel(String releaseLabel) {
                     this.releaseLabel = releaseLabel;
                     return this;
              }


              public TaskRunnerStepBuilder withS3NoProxy(boolean s3NoProxy) {
                     this.s3NoProxy = s3NoProxy;
                     return this;
              }

              public TaskRunnerStepBuilder withName(String name) {
                     this.name = name;
                     return this;
              }

       }
}



