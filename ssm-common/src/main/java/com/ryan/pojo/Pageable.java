package com.ryan.pojo;

/**
 * 需要分页的实体实现此接口
 * @author YoriChen
 * @date 2018/5/21
 */
public interface Pageable {

    /**
     * @param page
     * @return {@link #getPage()}
     */
    Page setPage(Page page);
    Page getPage();
}