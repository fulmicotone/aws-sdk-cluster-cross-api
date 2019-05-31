package com.fulmicotone.aws.cluster.cross.api.spec.cloudwatch.enumerator;


import java.util.Optional;

public class Metrics {

    public  enum EmrMetrics {

        ContainerAllocated,
        ContainerReserved,
        ContainerPending,
        ContainerPendingRatio,
        AppsCompleted,
        AppsFailed,
        AppsKilled,
        AppsPending,
        AppsRunning,
        AppsSubmitted,
        CoreNodesRunning,
        CoreNodesPending,
        LiveDataNodes,
        MRTotalNodes,
        MRActiveNodes,
        MRLostNodes,
        MRUnhealthyNodes,
        MRDecommissionedNodes,
        MRRebootedNodes,
        IsIdle,
        S3BytesWritten,
        S3BytesRead,
        HDFSUtilization,
        HDFSBytesRead,
        HDFSBytesWritten,
        MissingBlocks,
        CorruptBlocks,
        TotalLoad,
        MemoryTotalMB,
        MemoryReservedMB,
        MemoryAvailableMB,
        YARNMemoryAvailablePercentage,
        MemoryAllocatedMB,
        PendingDeletionBlocks,
        UnderReplicatedBlocks,
        DfsPendingReplicationBlocks,
        CapacityRemainingGB,
        HBase,
        HbaseBackupFailed,
        MostRecentBackupDuration,
        TimeSinceLastSuccessfulBackup,
        JobsRunning,
        JobsFailed,
        MapTasksRunning,
        MapTasksRemaining,
        MapSlotsOpen,
        RemainingMapTasksPerSlot,
        ReduceTasksRunning,
        ReduceTasksRemaining,
        ReduceSlotsOpen,
        TaskNodesRunning,
        TaskNodesPending,
        LiveTaskTrackers;




public String getVal( ){  return Optional.ofNullable( stringVal).orElse(name()); }


        private final String stringVal;

        EmrMetrics() {

            this.stringVal=null;
        }
        EmrMetrics(String stringVal) {

            this.stringVal=stringVal;
        }
    }

}
