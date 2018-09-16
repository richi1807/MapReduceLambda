package com.github.lambda.mapreduce.driver;

import com.github.lambda.mapreduce.lambda.MapReduceLambda;

import javax.inject.Inject;

public class IntegrationTest {


    @Inject
    MapReduceLambda mapReduceLambda;

    public IntegrationTest(MapReduceLambda mapReduceLambda) {
        this.mapReduceLambda = mapReduceLambda;
    }

    public static void main(String[] args) {

        String lambdaRoleArn = "arn:aws:iam::457510903363:role/HelloWorldLambdaRole";
        MapReduceLambda mapReduceLambda = new MapReduceLambda(lambdaRoleArn);

        MapReduceLambda.CreateMapReduceLambdaResponse mapReduceLambdaResponse = mapReduceLambda.createMapReduceLambda(
                MapReduceLambda.CreateMapReduceLambdaRequest.builder()
                        .awsCredentialsArn(
                                lambdaRoleArn
                        )
                        .build()
        );


    }
}
