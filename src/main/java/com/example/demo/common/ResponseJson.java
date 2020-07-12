package com.example.demo.common;

import lombok.Data;

@Data
public class ResponseJson {

    private static final long serialVersionUID = 1L;
    private int status = 0;
    private String desc;
    private Object data;

    public ResponseJson() {
    }

    public ResponseJson(int status, String desc, Object data) {
        this.status = status;
        this.desc = desc;
        this.data = data;
    }

    public static ResponseJson Success(String desc, Object obj) {
        return new ResponseJson(ResponseCodeEnum.SUCCESS.getCode(), desc, obj);
    }

    public static ResponseJson Success(Object obj) {
        return new ResponseJson(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getDesc(), obj);
    }

    public static ResponseJson Success() {
        return new ResponseJson(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getDesc(), (Object)null);
    }

    public static ResponseJson Error(int status, String desc, Object obj) {
        return new ResponseJson(status, desc, obj);
    }
}
