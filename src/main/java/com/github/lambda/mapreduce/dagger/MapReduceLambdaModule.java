package com.github.lambda.mapreduce.dagger;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.github.lambda.mapreduce.dao.MapReduceEntityDao;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class MapReduceLambdaModule {

    @Provides
    public MapReduceEntityDao getMapReduceEntityDao() {

        return new MapReduceEntityDao(getAmazonDynamoDbClient());

    }

    /**
     * @return
     * @implNote : Safe to use the default client here. The code runs within a Lambda
     */
    @Provides
    @Singleton
    private AmazonDynamoDB getAmazonDynamoDbClient() {
        return AmazonDynamoDBClientBuilder.defaultClient();
    }

    @Provides
    @Singleton
    private DynamoDBMapper getDynamoDBMapper() {

        return new DynamoDBMapper(
                getAmazonDynamoDbClient(),
                DynamoDBMapperConfig.DEFAULT
        );
    }
}
