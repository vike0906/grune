package com.vike.grune.router;


import com.vike.grune.handler.ClientHandler;
import com.vike.grune.handler.impl.ClientHandlerImpl;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 * @author: lsl
 * @createDate: 2019/12/11
 */
public class ClientRouter {

    public static Router router(Vertx vertx){

        Router router = Router.router(vertx);

        ClientHandler clientHandler = new ClientHandlerImpl();

        router.get("/info").handler(clientHandler.info());

        return router;
    }
}
