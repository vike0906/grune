package com.vike.grune;

import io.vertx.core.AbstractVerticle;

/**
 * @author: lsl
 * @createDate: 2019/12/9
 */
@Deprecated
public class MyFirstVerticle extends AbstractVerticle {


    @Override
    public void start(){
        vertx.createHttpServer().requestHandler(req ->{
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello World");
        }).listen(8090);
    }
}
