package com.github.lambda.mapreduce.driver;

import com.github.lambda.mapreduce.dagger.DaggerMapReduceLambdaComponent;
import com.github.lambda.mapreduce.dagger.MapReduceLambdaModule;
import com.github.lambda.mapreduce.lambda.MapReduceLambda;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IntegrationTest {


    @Inject
    MapReduceLambda mapReduceLambda;

    public static void main(String[] args) {

        IntegrationTest integrationTest = new IntegrationTest();

        DaggerMapReduceLambdaComponent.builder()
                .mapReduceLambdaModule(
                        new MapReduceLambdaModule()
                ).build()
                .inject(integrationTest);
        String lambdaRoleArn = "arn:aws:iam::457510903363:role/HelloWorldLambdaRole";

        MapReduceLambda.CreateMapReduceLambdaResponse mapReduceLambdaResponse = integrationTest.mapReduceLambda.createMapReduceLambda(
                MapReduceLambda.CreateMapReduceLambdaRequest.builder()
                        .awsCredentialsArn(
                                lambdaRoleArn
                        )
                        .build()
        );


    }
}
