package com.rsd.domain;

import com.rsd.utils.PageInput;

/**
 * @author tony
 * @data 2019-04-22
 * @modifyUser
 * @modifyDate
 */
public class BnzProductTypeModel extends BnzProductType {


    private static final long serialVersionUID = -3952146218531642516L;

    /**
     * 分页属性
     * */
    private PageInput pageInput;

    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }
}
