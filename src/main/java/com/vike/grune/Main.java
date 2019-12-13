package com.vike.grune;

import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author: lsl
 * @createDate: 2019/12/10
 */
public class Main {

    private final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(MyFirstVerticle.class.getName());

        vertx.deployVerticle(RouterVerticle.class.getName());
        
        log.info("服务启动成功");
    }
}
