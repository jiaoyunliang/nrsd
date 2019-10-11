package com.rsd.domain;

import com.rsd.utils.PageInput;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-26
 * @modifyUser
 * @modifyDate
 */
public class RsdOrgInfoModel extends RsdOrgInfo {


    private static final long serialVersionUID = -9175520598792815818L;

    /**
     * 分页属性
     * */
    private PageInput pageInput;

    private String roleName;

    private List<Long> orgTypeIds;

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }

    public List<Long> getOrgTypeIds() {
        return orgTypeIds;
    }

    public void setOrgTypeIds(List<Long> orgTypeIds) {
        this.orgTypeIds = orgTypeIds;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
