package com.suedar.blog.biz.base;

import lombok.Data;

@Data
public class Page<T> {

    private T data;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer totalNum;

    private Integer offset = 0;
}
