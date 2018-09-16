package com.github.lambda.mapreduce.sweepers;

import com.amazonaws.services.lambda.AWSLambdaClient;

import javax.inject.Inject;

public class LambaClientProxy {

    private static final String DESCRIPTION = "Lambda Function description";

    @Inject
    private AWSLambdaClient awsLambdaClient;

    @Inject
    public void setAwsLambdaClient(AWSLambdaClient awsLambdaClient) {
        this.awsLambdaClient = awsLambdaClient;
    }

    /**
     * @param
     * @return
     * @implSpec Variable exported through CloudFormation
     */
    public String getSweeperLambdaArn() {

        return System.getenv("SWEEPER_LAMBDA_CLOUDFORMATION_ARN");
    }
}
