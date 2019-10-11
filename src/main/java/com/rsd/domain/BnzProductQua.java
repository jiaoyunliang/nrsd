package com.rsd.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "BNZ_PRODUCT_QUA")
public class BnzProductQua {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORG_INFO_ID")
    private Long orgInfoId;

    @Column(name = "QUA_CODE")
    private Integer quaCode;

    @Column(name="ORG_NAME")
    private String orgName;

    @Column(name = "QUA_VALUE")
    private String quaValue;

    @Column(name = "QUA_TYPE")
    private Integer quaType;

    @Column(name = "VALID_DATE")
    private Date validDate;

    @Column(name = "typeFlag")
    private Integer typeFlag;

    @Column(name = "IS_SHOW")
    private Integer isShow;

    @Column(name = "IS_DEL")
    private Integer isDel;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "CREATE_TIME")
    private Date createTime;

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
     * @return ORG_INFO_ID
     */
    public Long getOrgInfoId() {
        return orgInfoId;
    }

    /**
     * @param orgInfoId
     */
    public void setOrgInfoId(Long orgInfoId) {
        this.orgInfoId = orgInfoId;
    }

    /**
     * @return QUA_CODE
     */
    public Integer getQuaCode() {
        return quaCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @param quaCode
     */
    public void setQuaCode(Integer quaCode) {
        this.quaCode = quaCode;
    }

    /**
     * @return QUA_VALUE
     */
    public String getQuaValue() {
        return quaValue;
    }

    /**
     * @param quaValue
     */
    public void setQuaValue(String quaValue) {
        this.quaValue = quaValue;
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

    public Integer getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(Integer typeFlag) {
        this.typeFlag = typeFlag;
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

    public Integer getQuaType() {
        return quaType;
    }

    public void setQuaType(Integer quaType) {
        this.quaType = quaType;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }
}