package com.rsd.domain;

import com.rsd.utils.PageInput;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-01
 * @modifyUser
 * @modifyDate
 */
public class RsdRoleModel extends RsdRole {


    private static final long serialVersionUID = 1019146053971749314L;
    /**
     * 分页属性
     * */
    private PageInput pageInput;

    private String sysName;

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }

    List<Long> resIds;

    public List<Long> getResIds() {
        return resIds;
    }

    public void setResIds(List<Long> resIds) {
        this.resIds = resIds;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
}
