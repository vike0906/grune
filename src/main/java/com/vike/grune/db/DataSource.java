package com.vike.grune.db;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.MySQLClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * @author: lsl
 * @createDate: 2019/12/12
 */
public class DataSource {

    private final static Logger log = LoggerFactory.getLogger(DataSource.class);

    /**MySQLPool1*/
    public static final int MS_ONE = 1;

    private static AsyncSQLClient MySQLPool1 = null;

    private static JsonObject MySQLPool1Config = new JsonObject();

    static {
        /**读取配置文件*/
        Properties prop = new Properties();
        try{
            InputStream is = DataSource.class.getClassLoader().getResourceAsStream("application.properties");
            prop.load(is);
            /**mysqlPool1*/
            MySQLPool1Config.put("host",prop.getProperty("mysqlPool1.host"));
            MySQLPool1Config.put("port",Integer.valueOf(prop.getProperty("mysqlPool1.port")));
            MySQLPool1Config.put("database",prop.getProperty("mysqlPool1.database"));
            MySQLPool1Config.put("driver_class",prop.getProperty("mysqlPool1.driver_class"));
            MySQLPool1Config.put("username",prop.getProperty("mysqlPool1.username"));
            MySQLPool1Config.put("password",prop.getProperty("mysqlPool1.password"));
            MySQLPool1Config.put("maxPoolSize",Long.valueOf(prop.getProperty("mysqlPool1.maxPoolSize")));
            MySQLPool1Config.put("queryTimeout",Long.valueOf(prop.getProperty("mysqlPool1.queryTimeout")));
            is.close();
        }
        catch(Exception e){
            log.error(e.getMessage());
        }
    }

    public void init(Vertx vertx){
        MySQLPool1 = MySQLClient.createShared(vertx, MySQLPool1Config, "MySQLPool1");
    }

    public static AsyncSQLClient instance(){
        return MySQLPool1;
    }
}
