package com.suedar.blog.biz.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResult<T> implements Serializable {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 数据
     */
    private T data;

    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String message;


    public BaseResult() {
        this.success = true;
    }

    private BaseResult(Boolean success, T data, int code, String message) {
        this.success = success;
        this.data = data;
        this.code = code;
        this.message = message;
    }

}
