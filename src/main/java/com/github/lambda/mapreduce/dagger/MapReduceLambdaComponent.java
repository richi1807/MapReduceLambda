package com.github.lambda.mapreduce.dagger;

import com.github.lambda.mapreduce.driver.IntegrationTest;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = MapReduceLambdaModule.class)
@Singleton
public interface MapReduceLambdaComponent {
    void inject(IntegrationTest integrationTest);
}
