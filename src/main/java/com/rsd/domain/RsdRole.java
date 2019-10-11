package com.rsd.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "RSD_ROLE")
public class RsdRole implements Serializable {


    private static final long serialVersionUID = 2359786204274856417L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ROLE_TYPE")
    private Integer roleType;

    @Column(name = "ROLE_CODE")
    private String roleCode;

    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "ACCOUNT_NUM")
    private Integer accountNum;

    @Column(name = "SYS_ID")
    private Integer sysId;

    @Column(name = "IS_DEL")
    private Integer isDel;



    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return ROLE_TYPE
     */
    public Integer getRoleType() {
        return roleType;
    }

    /**
     * @param roleType
     */
    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    /**
     * @return ROLE_CODE
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * @param roleCode
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * @return ACCOUNT_ID
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * @return ACCOUNT_NUM
     */
    public Integer getAccountNum() {
        return accountNum;
    }

    /**
     * @param accountNum
     */
    public void setAccountNum(Integer accountNum) {
        this.accountNum = accountNum;
    }

    /**
     * @return SYS_ID
     */
    public Integer getSysId() {
        return sysId;
    }

    /**
     * @param sysId
     */
    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

}