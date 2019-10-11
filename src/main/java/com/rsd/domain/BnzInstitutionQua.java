package com.rsd.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "BNZ_INSTITUTION_QUA")
public class BnzInstitutionQua {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORG_INFO_ID")
    private Long orgInfoId;
    @Column(name="ORG_NAME")
    private String orgName;
    @Column(name = "QUA_NAME")
    private String quaName;

    @Column(name = "VALIDITY_DATE")
    private Date validityDate;

    @Column(name = "QUA_TYPE")
    private BigDecimal quaType;

    @Column(name = "IS_SHOW")
    private Integer isShow;

    @Column(name = "IS_DEL")
    private Integer isDel;
    @Column(name="TYPE_FLAG")
    private Integer typeFlag;
    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    private String orgInfoName;

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

    public Long getOrgInfoId() {
        return orgInfoId;
    }

    public void setOrgInfoId(Long orgInfoId) {
        this.orgInfoId = orgInfoId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(Integer typeFlag) {
        this.typeFlag = typeFlag;
    }

    /**
     * @return QUA_NAME
     */
    public String getQuaName() {
        return quaName;
    }

    /**
     * @param quaName
     */
    public void setQuaName(String quaName) {
        this.quaName = quaName;
    }

    /**
     * @return VALIDITY_DATE
     */
    public Date getValidityDate() {
        return validityDate;
    }

    /**
     * @param validityDate
     */
    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    /**
     * @return QUA_TYPE
     */
    public BigDecimal getQuaType() {
        return quaType;
    }

    /**
     * @param quaType
     */
    public void setQuaType(BigDecimal quaType) {
        this.quaType = quaType;
    }

    /**
     * @return IS_SHOW
     */
    public Integer getIsShow() {
        return isShow;
    }

    /**
     * @param isShow
     */
    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    /**
     * @return IS_DEL
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * @param isDel
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * @return UPDATE_USER
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return CREATE_USER
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrgInfoName() {
        return orgInfoName;
    }

    public void setOrgInfoName(String orgInfoName) {
        this.orgInfoName = orgInfoName;
    }
}