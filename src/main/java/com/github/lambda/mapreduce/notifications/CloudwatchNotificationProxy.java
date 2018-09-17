package com.github.lambda.mapreduce.notifications;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.model.PutRuleRequest;
import com.amazonaws.services.cloudwatchevents.model.RuleState;

import javax.inject.Inject;
import java.util.UUID;

public class CloudwatchNotificationProxy {

    private AmazonCloudWatchEvents amazonCloudWatchEvents;

    @Inject
    public CloudwatchNotificationProxy(AmazonCloudWatchEvents amazonCloudWatchEvents) {
        this.amazonCloudWatchEvents = amazonCloudWatchEvents;
    }

    /*
     * Sets up sweeper role for every file minutes
     * */
    public String registerSweeperRule(String ruleDescription, String roleArn, String lambdaArn) {

        return amazonCloudWatchEvents.putRule(
                new PutRuleRequest()
                        .withDescription(ruleDescription)
                        .withEventPattern(
                                "aws.events")
                        .withName(UUID.randomUUID().toString())
                        .withRoleArn(roleArn)
                        .withScheduleExpression(
                                "*/5 * * * *"
                        )
                        .withState(
                                RuleState.ENABLED
                        )
        ).getRuleArn();
    }
}
