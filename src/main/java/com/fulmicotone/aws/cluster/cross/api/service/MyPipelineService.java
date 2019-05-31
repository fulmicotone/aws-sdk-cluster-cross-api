package com.fulmicotone.aws.cluster.service;


import com.amazonaws.services.datapipeline.DataPipeline;
import com.amazonaws.services.datapipeline.model.*;
import com.fulmicotone.aws.cluster.cross.api.MyPipeline;
import com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline.FnMapToAWSParameterValue;
import com.fulmicotone.aws.cluster.cross.api.function.mytoawssdk.datapipeline.FnMyPipelineToAWSPipelineObjectList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MyPipelineService {

     protected Logger logger=LoggerFactory.getLogger(MyPipelineService.class);
     final protected DataPipeline client;

     public MyPipelineService (DataPipeline client) {  this.client=client; }

    /**
     * create and define a pipeline
     * @param pipeline
     */
    public String createAndDefine(String name,
                                  MyPipeline pipeline,
                                  HashMap<String,Object> parameters) {

        String pipelineId = newPipeline(name);
        List<PipelineObject> objList = new FnMyPipelineToAWSPipelineObjectList().apply(pipeline);

        //objList.stream().map(PipelineObject::toString).forEach(System.out::println);

        List<ParameterValue> awsParams = new FnMapToAWSParameterValue().apply(parameters);

        putPipelineDefinition(pipelineId,objList,awsParams);

        return pipelineId;

    }

    private  String newPipeline(String name){

        CreatePipelineRequest createPipelineRequest = new CreatePipelineRequest()
                .withName(name).withUniqueId( UUID.randomUUID().toString());

        CreatePipelineResult createPipelineResult = client.createPipeline(createPipelineRequest);
        String pipelineId = createPipelineResult.getPipelineId();
        logger.info("Created pipeline id: " + pipelineId);
        return pipelineId;
    }


    private  void putPipelineDefinition(
            final String pipelineId,
            final List<PipelineObject> pipelineObjectList,
            final List<ParameterValue> params) {

        PutPipelineDefinitionRequest putPipelineDefinition = new PutPipelineDefinitionRequest()
                .withPipelineId(pipelineId).withParameterValues(params)
                .withPipelineObjects(pipelineObjectList);

        PutPipelineDefinitionResult putPipelineResult = client
                .putPipelineDefinition(putPipelineDefinition);

        if (putPipelineResult.isErrored()) {
            logger.error("Error found in pipeline definition: ");
            putPipelineResult.getValidationErrors().stream()
                    .peek(System.out::println)
                    .forEach(e -> logger.error("error",e));
            throw new RuntimeException("Error in pipeline definition.");
        }

        if (putPipelineResult.getValidationWarnings().size() > 0) {
            logger.warn("Warnings found in definition: ");
            putPipelineResult.getValidationWarnings().stream().forEach(e -> logger.warn("error",e));
        }

        logger.info("Created pipeline definition");
    }

    public  void activate(final String pipelineId) {
        ActivatePipelineRequest activatePipelineRequest = new ActivatePipelineRequest().withPipelineId(pipelineId);
        client.activatePipeline(activatePipelineRequest);
        logger.info("MyPipeline activated");
    }

}
