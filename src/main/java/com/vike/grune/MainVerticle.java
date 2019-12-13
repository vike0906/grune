package com.vike.grune;

import io.vertx.core.AbstractVerticle;

/**
 * @author: lsl
 * @createDate: 2019/12/9
 */
@Deprecated
public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(){

        vertx.deployVerticle(MyFirstVerticle.class.getName());
        vertx.deployVerticle(RouterVerticle.class.getName());

    }
}
