package com.vike.grune.handler;

import com.vike.grune.common.CommonResponse;
import com.vike.grune.common.LocalCache;
import com.vike.grune.util.StringUtils;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;


/**
 * @desc: <鉴权处理器/>
 * @author: lsl
 * @createDate: 2019/12/10
 *
 */

public class AuthHandler implements Handler<RoutingContext> {

    private final static String OPTIONS_MESSAGE = "Options request is allowed";

    private final static String[] ANON_URI = {"/auth/login","/auth/logout"};

    private final static long LOGIN_TIMEOUT = 1800;

    private final static int UN_LOGIN_CODE = 100;

    private final static int LOGIN_TIMEOUT_CODE = 101;

    private final static String UN_LOGIN_MESSAGE = "未登录";

    private final static String LOGIN_TIMEOUT_MESSAGE = "登陆已过期，请重新登陆";

    private AuthHandler (){

    }

    public static AuthHandler create(){
        return new AuthHandler();
    }

    @Override
    public void handle(RoutingContext rc) {

        HttpServerRequest request = rc.request();
        HttpServerResponse response = rc.response();

        response.putHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.putHeader("Access-Control-Allow-Credentials", "true");
        response.putHeader("Access-Control-Allow-Headers", "Content-Type, Accept-Language, AuthToken");
        response.putHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response.putHeader("Access-Control-Max-Age", "3600");
        response.putHeader("Content-Type", "application/json");
        response.putHeader("Content-Encoding", "UTF-8");

        if(HttpMethod.OPTIONS.equals(request.method())){

            response.end(CommonResponse.success(OPTIONS_MESSAGE).toJson());

        }else{

            String uri = request.uri();

            if(isAnonPath(uri)){

                rc.next();

            }else{

                /**获取请求头中AuthToken*/
                String authToken = request.getHeader("request");

                if(StringUtils.isEmpty(authToken)){

                    response.end(new CommonResponse(UN_LOGIN_CODE,UN_LOGIN_MESSAGE).toJson());

                }else{

                    Long timeStamp = LocalCache.getToken(authToken);

                    if(timeStamp==null){

                        response.end(new CommonResponse(LOGIN_TIMEOUT_CODE,LOGIN_TIMEOUT_MESSAGE).toJson());

                    }else if(System.currentTimeMillis()-timeStamp>LOGIN_TIMEOUT){

                        response.end(new CommonResponse(LOGIN_TIMEOUT_CODE,LOGIN_TIMEOUT_MESSAGE).toJson());
                        LocalCache.removeToken(authToken);

                    }else{

                        rc.next();
                    }
                }
            }
        }

    }

    private boolean isAnonPath(String uri){
        for(String path : ANON_URI){
            if(path.equals(uri)) return true;
        }
        return false;
    }
}
