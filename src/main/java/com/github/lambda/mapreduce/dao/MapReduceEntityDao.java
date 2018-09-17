package com.github.lambda.mapreduce.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.*;
import com.github.lambda.mapreduce.lambda.MapReduceLambda;
import com.github.lambda.mapreduce.model.MapReduceEntity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

import static com.github.lambda.mapreduce.model.MapReduceEntity.MAP_REDUCE_ENTITY_TABLE;
import static com.github.lambda.mapreduce.model.MapReduceEntity.MAP_REDUCE_LAMBDA_ARN;

//TODO: Add cloudWatch configuration
@Singleton
public class MapReduceEntityDao {

    private AmazonDynamoDB amazonDynamoDBClient;

    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(
            amazonDynamoDBClient, DynamoDBMapperConfig.DEFAULT
    );

    private String requestMapReduceLambdaArn;

    @Inject
    public MapReduceEntityDao(
            AmazonDynamoDB amazonDynamoDBClient) {
        this.amazonDynamoDBClient = amazonDynamoDBClient;
        initializeTableIfNotExists();
    }

    private void initializeTableIfNotExists() {

        long mapRedEntityTablesCount =
                amazonDynamoDBClient
                        .listTables()
                        .getTableNames()
                        .stream()
                        .filter(table_name -> table_name.equalsIgnoreCase(MAP_REDUCE_ENTITY_TABLE))
                        .count();

        if (0 == mapRedEntityTablesCount) {

            amazonDynamoDBClient.createTable(
                    new CreateTableRequest()
                            .withAttributeDefinitions(
                                    ImmutableList.<AttributeDefinition>builder()
                                            .add(
                                                    new AttributeDefinition()
                                                            .withAttributeName(MAP_REDUCE_LAMBDA_ARN)
                                                            .withAttributeType(ScalarAttributeType.S))
                                            .build())
                            .withKeySchema(
                                    ImmutableList.<KeySchemaElement>builder()
                                            .add(
                                                    new KeySchemaElement()
                                                            .withAttributeName(MAP_REDUCE_LAMBDA_ARN)
                                                            .withKeyType(KeyType.HASH))
                                            .build())
                            .withTableName(MAP_REDUCE_ENTITY_TABLE)
                            .withProvisionedThroughput(
                                    new ProvisionedThroughput()
                                            .withReadCapacityUnits(5L)
                                            .withWriteCapacityUnits(5L)));
        }
    }

    public void createLambdaMapReduceEntity(
            MapReduceLambda.CreateMapReduceLambdaRequest createMapReduceLambdaRequest) {

        requestMapReduceLambdaArn = UUID.randomUUID().toString();

        MapReduceEntity mapReduceEntity =
                MapReduceEntity.builder()
                        .createMapReduceLambdaRequest(createMapReduceLambdaRequest)
                        .mapReduceLambdaArn(requestMapReduceLambdaArn)
                        .allMapLamda(Lists.newArrayList())
                        .allReduceLambda(Lists.newArrayList())
                        .remainingMapLambda(createMapReduceLambdaRequest.getNumberOfMappers())
                        .remainingReduceLambda(createMapReduceLambdaRequest.getNumberOfReducers())
                        .build();

        dynamoDBMapper.save(
                mapReduceEntity,
                DynamoDBMapperConfig.DEFAULT
        );
    }

    public List<MapReduceEntity> listAllEntries() {

        return dynamoDBMapper.scan(
                MapReduceEntity.class,
                new DynamoDBScanExpression() //TODO: Probably incorrect
                ,
                DynamoDBMapperConfig.DEFAULT
        );

    }
}
