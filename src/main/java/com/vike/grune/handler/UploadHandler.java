package com.vike.grune.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Set;

/**
 * @author: lsl
 * @createDate: 2019/12/11
 */
public class UploadHandler implements Handler<RoutingContext> {

    private final static Logger log = LoggerFactory.getLogger(UploadHandler.class);

    private UploadHandler (){

    }

    public static UploadHandler create(){

        return new UploadHandler();

    }

    @Override
    public void handle(RoutingContext e) {

        Set<FileUpload> fileUploads = e.fileUploads();
        Iterator<FileUpload> iterator = fileUploads.iterator();
        while (iterator.hasNext()){
            FileUpload next = iterator.next();
            log.info("size {}",next.size());
            log.info("name {}",next.name());
            log.info("fileName {}",next.fileName());
            log.info("uploadedFileName {}",next.uploadedFileName());
        }
    }
}
