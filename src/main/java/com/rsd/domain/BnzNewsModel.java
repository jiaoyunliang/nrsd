package com.rsd.domain;

import com.rsd.utils.PageInput;

/**
 * @author tony
 * @data 2019-04-29
 * @modifyUser
 * @modifyDate
 */
public class BnzNewsModel extends BnzNews {


    private static final long serialVersionUID = 2733306935791003793L;

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
