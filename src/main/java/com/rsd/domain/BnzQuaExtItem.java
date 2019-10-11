package com.rsd.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "BNZ_QUA_EXT_ITEM")
public class BnzQuaExtItem {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "QUA_CODE")
    private Integer quaCode;

    @Column(name = "EXT_CODE")
    private String extCode;

    @Column(name = "EXT_NAME")
    private String extName;

    @Column(name = "IS_MUST")
    private String isMust;

    @Column(name = "ORDER_ID")
    private BigDecimal orderId;

    @Column(name = "QUA_TYPE")
    private Integer quaType;

    @Column(name = "IS_DEL")
    private BigDecimal isDel;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name="EXT_TYPE")
    private Integer extType;
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
     * @return QUA_CODE
     */
    public Integer getQuaCode() {
        return quaCode;
    }

    /**
     * @param quaCode
     */
    public void setQuaCode(Integer quaCode) {
        this.quaCode = quaCode;
    }

    /**
     * @return EXT_CODE
     */
    public String getExtCode() {
        return extCode;
    }

    /**
     * @param extCode
     */
    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

    /**
     * @return EXT_NAME
     */
    public String getExtName() {
        return extName;
    }

    /**
     * @param extName
     */
    public void setExtName(String extName) {
        this.extName = extName;
    }

    /**
     * @return IS_MUST
     */
    public String getIsMust() {
        return isMust;
    }

    /**
     * @param isMust
     */
    public void setIsMust(String isMust) {
        this.isMust = isMust;
    }

    /**
     * @return ORDER_ID
     */
    public BigDecimal getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(BigDecimal orderId) {
        this.orderId = orderId;
    }

    /**
     * @return QUA_TYPE
     */
    public Integer getQuaType() {
        return quaType;
    }

    /**
     * @param quaType
     */
    public void setQuaType(Integer quaType) {
        this.quaType = quaType;
    }

    /**
     * @return IS_DEL
     */
    public BigDecimal getIsDel() {
        return isDel;
    }

    /**
     * @param isDel
     */
    public void setIsDel(BigDecimal isDel) {
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

    public Integer getExtType() {
        return extType;
    }

    public void setExtType(Integer extType) {
        this.extType = extType;
    }
}