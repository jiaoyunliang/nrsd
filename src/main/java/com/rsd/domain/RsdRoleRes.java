package com.rsd.domain;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "RSD_ROLE_RES")
public class RsdRoleRes {
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "RES_ID")
    private Long resId;

    /**
     * @return ROLE_ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * @return RES_ID
     */
    public Long getResId() {
        return resId;
    }

    /**
     * @param resId
     */
    public void setResId(Long resId) {
        this.resId = resId;
    }
}