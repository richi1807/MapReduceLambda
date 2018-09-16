package com.github.lambda.mapreduce.notifications;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.model.PutRuleRequest;
import com.amazonaws.services.cloudwatchevents.model.RuleState;

import javax.inject.Inject;
import java.util.UUID;

public class CloudwatchNotificationProxy {

    @Inject
    private AmazonCloudWatchEvents amazonCloudWatchEvents;

    @Inject
    private String cloudWatchRoleArn;

    @Inject
    public CloudwatchNotificationProxy(AmazonCloudWatchEvents amazonCloudWatchEvents,
                                       String cloudWatchRoleArn) {
        this.amazonCloudWatchEvents = amazonCloudWatchEvents;
        this.cloudWatchRoleArn = cloudWatchRoleArn;
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
                        .withRoleArn(cloudWatchRoleArn)
                        .withScheduleExpression(
                                "*/5 * * * *"
                        )
                        .withState(
                                RuleState.ENABLED
                        )
        ).getRuleArn();
    }
}
