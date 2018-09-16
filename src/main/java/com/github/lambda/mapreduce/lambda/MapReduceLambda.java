package com.github.lambda.mapreduce.lambda;

import com.amazonaws.services.s3.AmazonS3URI;
import com.github.lambda.mapreduce.dao.MapReduceEntityDao;
import com.github.lambda.mapreduce.sweepers.DynamoDbTableSweeperOrchestrator;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.inject.Inject;

public class MapReduceLambda {

    @Inject
    private MapReduceEntityDao mapReduceEntityDao;

    @Inject
    private DynamoDbTableSweeperOrchestrator entitySweeperLambda;

    private String lambdaRoleArn;

    public MapReduceLambda(@NonNull String lambdaRoleArn) {
        this.lambdaRoleArn = lambdaRoleArn;
    }

    @Data
    @Builder
    public static class CreateMapReduceLambdaRequest {

        String awsCredentialsArn;
        String mapLambdaArn;
        String reduceLambdaArn;

        int numberOfMappers;
        int numberOfReducers;

        AmazonS3URI inputS3Prefix;
        AmazonS3URI outputPrefix;

        int maximumConcurrentExecutions;
    }

    @Data
    @Builder
    public static class CreateMapReduceLambdaResponse {
        String mapReduceLambdaArn;
    }

    /**
     * Does the following
     * <p>
     * 1. Calls entity service to save the request in dynamoDb
     * 2. Sets up a new Lambda on DynamoDb table to sweep it through
     *
     * @param createMapReduceLambdaRequest
     * @return
     */
    public CreateMapReduceLambdaResponse createMapReduceLambda(
            CreateMapReduceLambdaRequest createMapReduceLambdaRequest) {

        mapReduceEntityDao.createLambdaMapReduceEntity(createMapReduceLambdaRequest);
        return null;
    }
}
