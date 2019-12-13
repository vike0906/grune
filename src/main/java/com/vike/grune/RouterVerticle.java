package com.vike.grune;

import com.vike.grune.common.CommonResponse;
import com.vike.grune.db.DataSource;
import com.vike.grune.handler.AuthHandler;
import com.vike.grune.handler.UploadHandler;
import com.vike.grune.router.AuthRouter;
import com.vike.grune.router.ClientRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author: lsl
 * @createDate: 2019/12/10
 */
public class RouterVerticle extends AbstractVerticle {

    private final static Logger log = LoggerFactory.getLogger(RouterVerticle.class);

    /**消息体容量限制5M*/
    private final static long BODY_MAX_SIZE = 5*1024*1024;
    /**资源未找到消息*/
    private final static String URI_NOT_EXIST = "resource not exist";

    @Override
    public void start() {



        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        /**请求日志处理器*/
        router.route().handler(LoggerHandler.create());

        /**鉴权处理器*/
        router.route().handler(AuthHandler.create());

        /**消息体处理器*/
        router.route().handler(BodyHandler.create().setBodyLimit(BODY_MAX_SIZE));

        router.post("/upload").handler(UploadHandler.create());

        router.mountSubRouter("/auth", AuthRouter.router(vertx));

        router.mountSubRouter("/client", ClientRouter.router(vertx));

        /**处理器内部错误后会调用该处理器*/
        router.route().failureHandler(fail->{
            HttpServerResponse response = fail.response();
            log.error("error code: {}, error message: {}, error stack: {}",fail.statusCode(),fail.failure().getMessage(),fail.failure().getStackTrace()[1]);
            response.end(CommonResponse.fail(fail.failure().getMessage()).toJson());
        });

        /**404处理器*/
        router.errorHandler(404,error->{
            HttpServerResponse response = error.response();
            response.end(new CommonResponse(404,URI_NOT_EXIST).toJson());
        });

        DataSource dataSource = new DataSource();
        dataSource.init(vertx);

        server.requestHandler(router).listen(8086);
    }


}
