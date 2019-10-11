package com.rsd.domain;

import com.rsd.utils.PageInput;

/**
 * @author tony
 * @data 2019-04-23
 * @modifyUser
 * @modifyDate
 */
public class BnzAccountApplyModel extends BnzAccountApply {


    private static final long serialVersionUID = 7371543402354024138L;
    /**
     * 分页属性
     * */
    private PageInput pageInput;

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }
}
