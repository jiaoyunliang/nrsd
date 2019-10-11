package com.rsd.domain;

import com.rsd.utils.PageInput;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-06
 * @modifyUser
 * @modifyDate
 */
public class BnzInsideMsgModel extends BnzInsideMsg {


    private static final long serialVersionUID = -479803412619699630L;

    /**
     * 分页属性
     * */
    private PageInput pageInput;

    private String orgName;

    private List<Long> orgIds;

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

    public List<Long> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<Long> orgIds) {
        this.orgIds = orgIds;
    }
}
