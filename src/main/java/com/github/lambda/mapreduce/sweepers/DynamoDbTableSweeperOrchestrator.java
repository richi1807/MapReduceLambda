package com.github.lambda.mapreduce.sweepers;

import com.github.lambda.mapreduce.notifications.CloudwatchNotificationProxy;
import lombok.Data;
import lombok.NonNull;

import javax.inject.Singleton;

/**
 * Sweeps over a dynamoDb table table to fetch new tasks.
 * <p>
 * The way this works is this :-
 * <p>
 * Two callback events are registered :-
 * <p>
 * <p>
 * 1. Register a callback to cloudwatch events from DynamoDb entity table
 * When new events come, it takes the following actions :-
 * <p>
 * a. See if more lambda functions can be run.
 * b. If more lambda functions can be run :-
 * 1. Run these lambda functions and register a callback on their completion
 * 2. Updates the count of available Lambda slots
 * <p>
 * 2. When triggered through a lambda callback,
 * a. Updates the entity table (With consistency checks)
 * <p>
 * TODO: Decide where the output is sent is coalesced at
 * <p>
 * On new event in cloudWatch,
 */
@Singleton
@Data
public class DynamoDbTableSweeperOrchestrator {

    @NonNull
    private CloudwatchNotificationProxy cloudwatchNotificationProxy;

    @NonNull
    private LambaClientProxy lambaClientProxy;

    public DynamoDbTableSweeperOrchestrator(CloudwatchNotificationProxy cloudWatchNotificationProxy, LambaClientProxy lambdaClientProxy) {

        this.cloudwatchNotificationProxy = cloudwatchNotificationProxy;
        this.lambaClientProxy = lambaClientProxy;
    }

    /**
     * 1. Registers sweeper function
     */
    private void initializeSweeperSetUp(String roleArn) {

        lambaClientProxy.getSweeperLambdaArn();
        this.cloudwatchNotificationProxy.registerSweeperRule(
                "Sweeper rule on dynamoDb",
                roleArn,
                lambaClientProxy.getSweeperLambdaArn()
        );

    }


}
