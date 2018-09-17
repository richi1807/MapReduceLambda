package com.github.lambda.mapreduce.dagger;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.github.lambda.mapreduce.dao.MapReduceEntityDao;
import com.github.lambda.mapreduce.lambda.MapReduceLambda;
import com.github.lambda.mapreduce.notifications.CloudwatchNotificationProxy;
import com.github.lambda.mapreduce.sweepers.DynamoDbTableSweeperOrchestrator;
import com.github.lambda.mapreduce.sweepers.LambaClientProxy;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
@Singleton
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
    public AmazonDynamoDB getAmazonDynamoDbClient() {
        return AmazonDynamoDBClientBuilder.defaultClient();
    }

    @Provides
    @Singleton
    public DynamoDBMapper getDynamoDBMapper() {

        return new DynamoDBMapper(
                getAmazonDynamoDbClient(),
                DynamoDBMapperConfig.DEFAULT
        );
    }

    @Provides
    @Singleton
    public MapReduceLambda getMapReduceLambda() {
        return new MapReduceLambda(
                getMapReduceEntityDao(),
                getSweeperOrchestrator()

        );
    }

    private DynamoDbTableSweeperOrchestrator getSweeperOrchestrator() {

        return new DynamoDbTableSweeperOrchestrator(
                getCloudWatchNotificationProxy(),
                getLambdaClientProxy()
        );
    }

    private LambaClientProxy getLambdaClientProxy() {
        return new LambaClientProxy(
                getAWSLambdaClient()
        );
    }

    private AWSLambda getAWSLambdaClient() {
        return AWSLambdaClientBuilder.standard()
                       .withCredentials(getAWSCredentials())
                       .build();
    }

    private CloudwatchNotificationProxy getCloudWatchNotificationProxy() {
        return new CloudwatchNotificationProxy(
                getAmazonCloudWatchEventsClient()
        );
    }

    private AmazonCloudWatchEvents getAmazonCloudWatchEventsClient() {
        return AmazonCloudWatchEventsClientBuilder.standard()
                       .withCredentials(getAWSCredentials())
                       .build();
    }

    private AWSCredentialsProvider getAWSCredentials() {
        return new DefaultAWSCredentialsProviderChain();
    }
}
