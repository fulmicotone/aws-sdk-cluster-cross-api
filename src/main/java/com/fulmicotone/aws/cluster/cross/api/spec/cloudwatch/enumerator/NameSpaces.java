package com.fulmicotone.aws.cluster.cross.api.spec.cloudwatch.enumerator;

public enum NameSpaces {

  ApiGateway("AWS/ApiGateway"),
  AppStream("AWS/AppStream"),
  Billing("AWS/Billing"),
  CloudFront("AWS/CloudFront"),
  CloudSearch("AWS/CloudSearch"),
  Events("AWS/Events"),
  Logs("AWS/Logs"),
  CodeBuild("AWS/CodeBuild"),
  Cognito("AWS/Cognito"),
  Connect("AWS/Connect"),
  DMS("AWS/DMS"),
  DX("AWS/DX"),
  DynamoDB("AWS/DynamoDB"),
  EC2("AWS/EC2"),
  EC2Spot("AWS/EC2Spot"),
  AutoScaling("AWS/AutoScaling"),
  ElasticBeanstalk("AWS/ElasticBeanstalk"),
  EBS("AWS/EBS"),
  ECS("AWS/ECS"),
  EFS("AWS/EFS"),
  ElasticInference("AWS/ElasticInference"),
  ApplicationELB("AWS/ApplicationELB"),
  ELB("AWS/ELB"),
  NetworkELB("AWS/NetworkELB"),
  ElasticTranscoder("AWS/ElasticTranscoder"),
  ElastiCache("AWS/ElastiCache"),
  ES("AWS/ES"),
  ElasticMapReduce("AWS/ElasticMapReduce"),
  MediaConnect("AWS/MediaConnect"),
MediaConvert("AWS/MediaConvert"),
MediaPackage("AWS/MediaPackage"),
MediaTailor("AWS/MediaTailor"),
FSx("AWS/FSx"),
GameLift("AWS/GameLift"),
Glue("AWS/Glue"),
Inspector("AWS/Inspector"),
IoT("AWS/IoT"),
IoTAnalytics("AWS/IoTAnalytics"),
ThingsGraph("AWS/ThingsGraph"),
KMS("AWS/KMS"),
KinesisAnalytics("AWS/KinesisAnalytics"),
Firehose("AWS/Firehose"),
Kinesis("AWS/Kinesis"),
KinesisVideo("AWS/KinesisVideo"),
Lambda("AWS/Lambda"),
Lex("AWS/Lex"),
ML("AWS/ML"),
AmazonMQ("AWS/AmazonMQ"),
Neptune("AWS/Neptune"),
OpsWorks("AWS/OpsWorks"),
Polly("AWS/Polly"),
Redshift("AWS/Redshift"),
RDS("AWS/RDS"),
Route53("AWS/Route53"),
SageMaker("AWS/SageMaker"),
DDoSProtection("AWS/DDoSProtection"),
SES("AWS/SES"),
SNS("AWS/SNS"),
SQS("AWS/SQS"),
S3("AWS/S3"),
SWF("AWS/SWF"),
States("AWS/States"),
StorageGateway("AWS/StorageGateway"),
Translate("AWS/Translate"),
TrustedAdvisor("AWS/TrustedAdvisor"),
NATGateway("AWS/NATGateway"),
TransitGateway("AWS/TransitGateway"),
WorkSpaces("AWS/WorkSpaces"),
VPN("AWS/VPN");


public String getVal(){

    return namespace;

}



    private final String namespace;
    NameSpaces(String namespace){


        this.namespace=namespace;
    }
}
