package com.github.lambda.mapreduce.sweepers;

import com.amazonaws.services.lambda.AWSLambda;
import lombok.Data;

import javax.inject.Inject;

@Data
public class LambaClientProxy {

    private static final String DESCRIPTION = "Lambda Function description";

    private AWSLambda awsLambdaClient;

    @Inject
    public LambaClientProxy(AWSLambda awsLambdaClient) {
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
