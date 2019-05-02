package com.suedar.blog.biz.base;

import lombok.Data;

public class Page<T> {

    private T result;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer totalNum;

    private Integer offset = 0;

    public Page() {
    }

    public Page(Integer pageNum, Integer pageSize) {
        this.pageNum = (pageNum == null || pageNum <= 0) ? 1 : pageNum;
        this.pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        this.offset = (this.pageNum - 1) * this.pageSize;
    }

    public Page(Integer pageNum, Integer pageSize, Integer totalNum) {
        this.pageNum = (pageNum == null || pageNum <= 0) ? 1 : pageNum;
        this.pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        this.offset = (this.pageNum - 1) * this.pageSize;
        this.totalNum = totalNum;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = (pageNum == null || pageNum <= 0) ? 1 : pageNum;
        this.offset = (this.pageNum - 1) * this.pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        this.offset = (this.pageNum - 1) * this.pageSize;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
