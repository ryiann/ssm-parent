package com.ryan.criteria;

import com.ryan.pojo.Page;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author YoriChen
 * @date 2018/5/21
 */
public class BaseCriteria extends Page implements Serializable {
    private static final long serialVersionUID = 3951999513152846225L;

    /**
     * @Fields orderType : TODO(排序)
     */
    private String orderBy;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}