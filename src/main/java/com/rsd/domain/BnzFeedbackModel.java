package com.rsd.domain;

import com.rsd.utils.PageInput;

/**
 * @author tony
 * @data 2019-05-06
 * @modifyUser
 * @modifyDate
 */
public class BnzFeedbackModel extends BnzFeedback {


    private static final long serialVersionUID = 4472079781433705402L;

    /**
     * 分页属性
     * */
    private PageInput pageInput;

    private String orgName;

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
