package com.github.lambda.mapreduce.lambda;


import com.github.lambda.mapreduce.dao.MapReduceEntityDao;
import com.github.lambda.mapreduce.model.MapReduceEntity;
import com.github.lambda.mapreduce.sweepers.LambaClientProxy;

import javax.inject.Inject;
import java.util.List;

public class SweeperLambda {

    private MapReduceEntityDao mapReduceEntityDao;
    private LambaClientProxy lambaClientProxy;

    @Inject
    public SweeperLambda(MapReduceEntityDao mapReduceEntityDao,
                         LambaClientProxy lambaClientProxy) {
        this.mapReduceEntityDao = mapReduceEntityDao;
        this.lambaClientProxy = lambaClientProxy;
    }

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
