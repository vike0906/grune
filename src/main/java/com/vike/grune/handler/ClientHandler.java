package com.vike.grune.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;


/**
 * @author: lsl
 * @createDate: 2019/12/11
 */
public interface ClientHandler{

    Handler<RoutingContext> info();

}
