package com.vike.grune.router;

import com.vike.grune.common.Assert;
import com.vike.grune.common.CommonResponse;
import com.vike.grune.common.ExceptionEnum;
import com.vike.grune.db.DataSource;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author: lsl
 * @createDate: 2019/12/12
 */
public class AuthRouter {

    private final static Logger log = LoggerFactory.getLogger(AuthRouter.class);

    public static Router router(Vertx vertx){

        Router router = Router.router(vertx);

        router.post("/login").handler(rc->{

            JsonObject body = rc.getBodyAsJson();
            Assert.check(body==null, ExceptionEnum.PARAMS_MISSING);

            String name = body.getString("name");
            String password = body.getString("password");
            Assert.check(name==null||password==null, ExceptionEnum.PARAMS_MISSING);

            DataSource.instance().getConnection(sc->{
                if(sc.failed()){
                    rc.response().end(CommonResponse.fail(sc.cause().getMessage()).toJson());
                    return;
                }
                final SQLConnection connect = sc.result();
                String sql  = "select t.password, t.salt from b_sys_user t where t.login_name = ? and t.status = 1 and t.is_delete = 1";
                connect.queryWithParams(sql,new JsonArray().add(name),rs->{
                    if(rs.failed()){
                        rc.response().end(CommonResponse.fail(rs.cause().getMessage()).toJson());
                        return;
                    }
                    ResultSet result = rs.result();
                    List<JsonArray> js = result.getResults();
                    String psd  = js.get(0).getString(0);
                    String sa = js.get(0).getString(1);
                    rc.response().end(CommonResponse.success(sa+" "+psd).toJson());
                    connect.close(cc->{
                        if(cc.failed()){
                            throw new RuntimeException(cc.cause());
                        }
                    });
                });
            });
        });

        router.delete("/logout").handler(rc->{

        });

        router.post("/change-psd").handler(rc->{

        });

        return router;
    }
}
