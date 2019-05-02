package com.suedar.blog.biz.base;


public class Page<T> {

    private T result;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer totalNum;

    private Integer offset = 0;

    private Boolean isPage = Boolean.TRUE;

    public Page() {
    }

    public Page(Integer pageNum, Integer pageSize, Boolean isPage) {
        this.pageNum = (pageNum == null || pageNum <= 0) ? 1 : pageNum;
        this.pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        this.offset = (this.pageNum - 1) * this.pageSize;
        this.isPage = isPage == null ? true : isPage;
    }

    public Page(Integer pageNum, Integer pageSize) {
        this.pageNum = (pageNum == null || pageNum <= 0) ? 1 : pageNum;
        this.pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        this.offset = (this.pageNum - 1) * this.pageSize;
        this.isPage = true;
    }

    public Page(Integer pageNum, Integer pageSize, Integer totalNum, Boolean isPage) {
        this.pageNum = (pageNum == null || pageNum <= 0) ? 1 : pageNum;
        this.pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        this.offset = (this.pageNum - 1) * this.pageSize;
        this.totalNum = totalNum;
        this.isPage = isPage == null ? true : isPage;
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

    public Boolean getIsPage() {
        return isPage;
    }

    public void setIsPage(Boolean page) {
        isPage = page;
    }
}
