package com.fulmicotone.aws.cluster.cross.api.function;

import com.fulmicotone.aws.cluster.cross.api.builder.MyCluster;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.emr.ApplicationsPH;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.emr.ReleasePH;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterSwConf;

import java.util.function.Supplier;

/**
 * Same parent class's behaviour plus the parameterizable Spark Default configuration
 *
 *  myEmrConfigExecutorCore,
 *     myEmrConfigExecutorMemory,
 *     myEmrConfigDynamicAllocationMaxExecutors,
 *     myEmrConfigExecutorOverhead
 * right now i added only these parameters because are those i need.
 *
 ***/
public class SupplierParameterizableEmrBuilderWithSparkDefault implements Supplier<MyCluster.MyClusterBuilder> {
    @Override
    public MyCluster.MyClusterBuilder get() {


      return new SupplierParameterizableEmrResourceBuilder()
                .get()
                .withClusterSwConf(ClusterSwConf
                        .ClusterSwConfBuilder.newOne()
                        .withReleaseLabel(new ReleasePH())
                        .withApplications(new ApplicationsPH())
                        .addConfiguration(new SupplierEmrHadoopEnvJava8Conf().get())
                        .addConfiguration(new SupplierEmrSparkEnvJava8Conf().get())
                        .addConfiguration(new SupplierParametrizableSparkDefaultsConfig()
                                .get()).build());

    }
}
