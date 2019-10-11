package com.rsd.domain;

import com.rsd.utils.PageInput;

import java.io.Serializable;

/**
 * @author admin
 * @ClassName hdx
 * @Description 产品与资质关系表
 * @Date 2019/5/9 13:47
 * @Version 1.0
 **/
public class BnzQuaProductModel extends BnzQuaProduct implements Serializable {
    private static final long serialVersionUID = -911864809349958831L;
    /**
     * 分页属性
     */
    private PageInput pageInput;
    private RsdDictInfo rsdDictInfo;

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }

    public RsdDictInfo getRsdDictInfo() {
        return rsdDictInfo;
    }

    public void setRsdDictInfo(RsdDictInfo rsdDictInfo) {
        this.rsdDictInfo = rsdDictInfo;
    }
}
