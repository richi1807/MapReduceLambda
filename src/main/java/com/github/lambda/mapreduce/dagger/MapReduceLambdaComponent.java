package com.github.lambda.mapreduce.dagger;

import com.github.lambda.mapreduce.lambda.MapReduceLambda;
import dagger.Component;

@Component(modules = MapReduceLambdaComponent.class)
public interface MapReduceLambdaComponent {
    void inject(MapReduceLambda mapReduceLambda);
}
