package com.github.lambda.mapreduce.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.github.lambda.mapreduce.lambda.MapReduceLambda;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import static com.github.lambda.mapreduce.model.MapReduceEntity.MAP_REDUCE_ENTITY_TABLE;

@DynamoDBTable(tableName = MAP_REDUCE_ENTITY_TABLE)
@Data
@Builder
public class MapReduceEntity {

    public static final String MAP_REDUCE_ENTITY_TABLE = "MapReduceEntity";
    public static final String MAP_REDUCE_LAMBDA_ARN = "mapReduceLambdaArn";

    MapReduceLambda.CreateMapReduceLambdaRequest createMapReduceLambdaRequest;

    int remainingMapLambda;
    List<String> allMapLamda;

    int remainingReduceLambda;
    List<String> allReduceLambda;

    String periodicSweeperRuleArn;

    @DynamoDBHashKey(attributeName = MAP_REDUCE_LAMBDA_ARN)
    @DynamoDBAttribute
    String mapReduceLambdaArn;
}
