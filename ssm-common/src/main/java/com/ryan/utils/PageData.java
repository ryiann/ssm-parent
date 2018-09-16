package com.ryan.utils;

import java.io.Serializable;

/**
 * 分页工具类
 * @author YoriChen
 * @date 2018/6/21
 */
public class PageData<T> implements Serializable {

    /**当前页码 */
    private Integer pageNum;

    /**每页记录数 */
    private Integer pageSize;

    /**总页数 */
    private Integer pageCount;

    /**
     * 页码索引数
     * 即页面上只显示7个页码，其他用[<<、<、>、>>]替代
     * 默认显示7页
     */
    private Integer indexCount;

    /**数据 */
    private T data;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(Integer indexCount) {
        this.indexCount = indexCount;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 返回 数据
     * @param data
     */
    public PageData(T data) {
        this.data = data;
    }

    /**
     * 返回 当前页码、每页记录数、总页数、页码索引、数据
     * @param data
     */
    public PageData(Integer pageNum, Integer pageSize, Integer pageCount, Integer indexCount, T data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
        this.indexCount = indexCount;
        this.data = data;
    }

}