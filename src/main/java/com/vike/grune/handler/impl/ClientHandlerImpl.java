package com.vike.grune.handler.impl;

import com.vike.grune.common.CommonResponse;
import com.vike.grune.handler.ClientHandler;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

/**
 * @author: lsl
 * @createDate: 2019/12/11
 */
public class ClientHandlerImpl implements ClientHandler {

    @Override
    public Handler<RoutingContext> info(){

        return e -> {
            HttpServerResponse response = e.response();
            response.end(CommonResponse.success("用户信息").toJson());
        };
    }
}
