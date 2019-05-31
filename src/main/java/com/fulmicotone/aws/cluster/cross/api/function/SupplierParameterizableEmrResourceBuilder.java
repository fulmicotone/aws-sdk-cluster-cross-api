package com.fulmicotone.aws.cluster.cross.api.function;

import com.fulmicotone.aws.cluster.cross.api.builder.MyCluster;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.placeholder.emr.*;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterBehaviourConf;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterHwConf;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterSecurityConf;
import com.fulmicotone.aws.cluster.cross.api.spec.datapipeline.resource.ClusterSwConf;

import java.util.function.Supplier;

/**
 * This supply an EMR Cluster Builder
 * already filled in following  areas:
 * - Security Configuration
 * - Cluster Behaviour
 * - Cluster Software Configuration
 * - Cluster Hardware configuration
 *
 * Each area instead of having values in  its fields, has a place holder.
 * By this way we can use MyParams to replace the place holder and use
 * using the Cluster builder has shape for any cluster.
 *
 * we assumed that we want to use a spot instances, below the required params
 * for using this shape.
 *     myEmrCoreType,
 *     myEmrMasterType,
 *     myEmrCoreCount,
 *     myEmrMasterBid,
 *     myEmrCoreBid,
 *     myEmrKeyPair,
 *     myEmrSubnet,
 *     myEmrRole,
 *     myEmrResourceRole,
 *     myEmrReleaseLabel,
 *     myEmrApplications,
 *     myMaxRetries,
 *     myTerminateAfter,
 *     myFailureAndRerunMode
 */
public class SupplierParameterizableEmrResourceBuilder implements Supplier<MyCluster.MyClusterBuilder> {
    @Override
    public MyCluster.MyClusterBuilder get() {


        return    MyCluster.MyClusterBuilder.newOne()
                .withClusterSecurityConf( ClusterSecurityConf
                        .ClusterSecuityConfBuilder.newOne()
                        .withKeyPair(new KeyPairPH())
                        .withSubnetId(new SubnetPH())
                        .withRole(new RolePH())
                        .withResourceRole(new ResourceRolePH())
                        .withRegion(new RegionPH())
                        .build())
                .withClusterHwConf(ClusterHwConf.ClusterHwConfBuilder
                        .newOne()
                        .withCoreInstanceType(new CoreTypePH())
                        .withMasterInstanceType(new MasterTypePH())
                        .withCoreInstanceCount(new CoreCountPH())
                        .withMasterInstanceBidPrice(new MasterBidPH())
                        .withCoreInstanceBidPrice(new CoreBidPH())
                        .build())
                .withClusterBehaviourConf(ClusterBehaviourConf.ClusterBehaviourConfBuilder.newOne()
                        .withMaximumRetries(new MaxRetriesPH())
                        .withTerminateAfter(new TerminateAfterPH())
                        .withFailureAndRerunMode(new FailureAndRerunModePH()) .build())
                .withClusterSwConf( ClusterSwConf
                        .ClusterSwConfBuilder.newOne()
                        .withPipelineLogUri(new LogPipelineUriPH())
                        .withReleaseLabel(new ReleasePH())
                        .withApplications(new ApplicationsPH()).build());

    }
}
