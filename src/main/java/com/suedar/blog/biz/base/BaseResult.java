package com.suedar.blog.biz.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResult<T> implements Serializable {

    private Boolean success;

    private T data;

    private String message;


    public BaseResult() {
        this.success = true;
    }

    private BaseResult(Boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static <T> BaseResult<T> rightReturn(T data) {
        return new BaseResult<>(Boolean.TRUE, data, "");
    }

    public static <T> BaseResult<T> errorReturn(String message) {
        return new BaseResult<>(Boolean.FALSE, null, message);
    }

}
