package com.github.lambda.mapreduce.lambda;


import com.github.lambda.mapreduce.dao.MapReduceEntityDao;
import com.github.lambda.mapreduce.model.MapReduceEntity;

import javax.inject.Inject;
import java.util.List;

public class SweeperLambda {

    @Inject
    private MapReduceEntityDao mapReduceEntityDao;

    public static class SweeperLambdaRequest {

    }

    public static class SweeperLambdaResponse {

    }

    /**
     * TODO: Implement the request/response class
     *
     * @param sweeperLambdaRequest
     * @param sweeperLambdaResponse
     */
    public void handleRequest(SweeperLambdaRequest sweeperLambdaRequest,
                              SweeperLambdaResponse sweeperLambdaResponse) {


        List<MapReduceEntity> mapReduceEntities = this.mapReduceEntityDao.listAllEntries(); //TODO: Scaling issue here


    }
}
