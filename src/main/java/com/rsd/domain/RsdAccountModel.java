package com.rsd.domain;

import com.rsd.utils.PageInput;

/**
 * @author tony
 * @data 2019-04-08
 * @modifyUser
 * @modifyDate
 */
public class RsdAccountModel extends RsdAccount {


    private static final long serialVersionUID = -2384130886056322892L;
      /**
     * 分页属性
     * */
    private PageInput pageInput;

    private String sysName;

    private String orgName;

    private RsdRole role;

    private RsdOrgInfo orgInfo;

    private String roleName;

    private String originPwd;

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }

    public RsdRole getRole() {
        return role;
    }

    public void setRole(RsdRole role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public RsdOrgInfo getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(RsdOrgInfo orgInfo) {
        this.orgInfo = orgInfo;
    }

    public String getOriginPwd() {
        return originPwd;
    }

    public void setOriginPwd(String originPwd) {
        this.originPwd = originPwd;
    }
}
