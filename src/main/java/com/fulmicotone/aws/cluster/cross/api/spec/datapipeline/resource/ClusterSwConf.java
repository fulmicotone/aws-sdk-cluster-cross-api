package com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource;

import com.fulmicotone.aws.cluster.cross.api.models.EmrConfiguration;
import com.fulmicotone.aws.cluster.cross.api.models.fields.MyString;

import java.util.ArrayList;
import java.util.List;

public class ClusterSwConf {


    public MyString amiVersion;
    public  MyString applications;
    public List<EmrConfiguration> configuration;
    public List<EmrConfiguration> coreGroupConfiguration;
    public List<EmrConfiguration> coreEbsConfiguration;
    public MyString releaseLabel;
    public   MyString pipelineLogUri;

    public ClusterSwConf(MyString amiVersion,
                         MyString applications,
                         List<EmrConfiguration> configuration,
                         List<EmrConfiguration> coreGroupConfiguration,
                         List<EmrConfiguration> coreEbsConfiguration,
                         MyString releaseLabel,
                         MyString pipelineLogUri) {
        this.amiVersion = amiVersion;
        this.applications = applications;
        this.configuration = configuration;
        this.coreGroupConfiguration = coreGroupConfiguration;
        this.coreEbsConfiguration = coreEbsConfiguration;
        this.releaseLabel = releaseLabel;
        this.pipelineLogUri = pipelineLogUri;
    }


    public ClusterSwConf(){}

    public static final class ClusterSwConfBuilder {
        private MyString amiVersion=new MyString();
        private  MyString applications=new MyString();;
        private List<EmrConfiguration>  configuration=new ArrayList<>();
        private List<EmrConfiguration>  coreGroupConfiguration=new ArrayList<>();
        private List<EmrConfiguration>  coreEbsConfiguration=new ArrayList<>();
        private MyString releaseLabel=new MyString();;
        private   MyString pipelineLogUri=new MyString();;

        private ClusterSwConfBuilder() { }

        public static ClusterSwConfBuilder newOne() {
            return new ClusterSwConfBuilder();
        }

        public ClusterSwConfBuilder withAmiVersion(MyString amiVersion) {
            this.amiVersion = amiVersion;
            return this;
        }

        public ClusterSwConfBuilder withApplications(MyString applications) {
            this.applications = applications;
            return this;
        }

        public ClusterSwConfBuilder addConfiguration(EmrConfiguration configuration) {
            this.configuration.add( configuration);
            return this;
        }

        public ClusterSwConfBuilder addCoreGroupConfiguration(EmrConfiguration coreGroupConfiguration) {
            this.coreGroupConfiguration.add(coreGroupConfiguration);
            return this;
        }

        public ClusterSwConfBuilder addCoreEbsConfiguration(EmrConfiguration coreEbsConfiguration) {
            this.coreEbsConfiguration.add(coreEbsConfiguration);
            return this;
        }

        public ClusterSwConfBuilder withReleaseLabel(MyString releaseLabel) {
            this.releaseLabel = releaseLabel;
            return this;
        }

        public ClusterSwConfBuilder withPipelineLogUri(MyString pipelineLogUri) {
            this.pipelineLogUri = pipelineLogUri;
            return this;
        }

        public ClusterSwConf build() {
           return  new ClusterSwConf(
                    this.amiVersion,
                    this.applications,
                    this.configuration,
                    this.coreGroupConfiguration,
                    this.coreEbsConfiguration,
                    this.releaseLabel,
                    this.pipelineLogUri);

        }
    }
}
