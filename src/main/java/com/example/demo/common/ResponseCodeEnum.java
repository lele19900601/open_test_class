package com.example.demo.common;

public enum ResponseCodeEnum {

    SUCCESS("操作成功", 200),
    NOAUTH("权限不足，无法访问，请联系管理员", 401);
    /** @deprecated */

    private String desc;
    private int code;

    ResponseCodeEnum(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
