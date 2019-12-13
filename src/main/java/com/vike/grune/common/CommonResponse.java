package com.vike.grune.common;

import com.google.gson.Gson;

/**
 * @author: lsl
 * @createDate: 2019/3/19
 * @description: 统一返回形式
 */
public class CommonResponse<T> {

    private final static Gson gs =  new Gson();
    private Integer code;
    private String message;
    private T content;

    public final static Integer successCode = 0;
    public final static Integer failCode = 500;
    public final static String successMessage = "success";

    public static CommonResponse success(){
        return new CommonResponse(successCode,successMessage);
    }
    public static CommonResponse success(String successMessage){
        return new CommonResponse(successCode,successMessage);
    }
    public static CommonResponse fail(String errorMessage){
        return new CommonResponse(failCode,errorMessage);
    }

    public CommonResponse(T content){
        this.code = successCode;
        this.message = successMessage;
        this.content = content;
    }

    public CommonResponse(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public String toJson(){
        return gs.toJson(this);
    }
}
