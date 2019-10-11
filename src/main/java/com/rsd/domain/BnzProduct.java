package com.rsd.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "BNZ_PRODUCT")
public class BnzProduct implements java.io.Serializable {


    private static final long serialVersionUID = 2762181622734769008L;
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "PRODUCT_TYPE_ID")
    private Long productTypeId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "GENERIC_NAME")
    private String genericName;

    @Column(name = "BRAND_NAME")
    private String brandName;

    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @Column(name = "IS_HOSPITAL_VIEW")
    private Integer isHospitalView;

    @Column(name = "IS_ORG_VIEW")
    private Integer isOrgView;

    @Column(name = "IS_HOT")
    private Integer isHot;

    @Column(name = "REMARK")
    private String remark;

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
     * @return ORG_ID
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return PRODUCT_TYPE_ID
     */
    public Long getProductTypeId() {
        return productTypeId;
    }

    /**
     * @param productTypeId
     */
    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    /**
     * @return PRODUCT_NAME
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return GENERIC_NAME
     */
    public String getGenericName() {
        return genericName;
    }

    /**
     * @param genericName
     */
    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    /**
     * @return BRAND_NAME
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @param brandName
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * @return MANUFACTURER
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return IS_HOSPITAL_VIEW
     */
    public Integer getIsHospitalView() {
        return isHospitalView;
    }

    /**
     * @param isHospitalView
     */
    public void setIsHospitalView(Integer isHospitalView) {
        this.isHospitalView = isHospitalView;
    }

    /**
     * @return IS_ORG_VIEW
     */
    public Integer getIsOrgView() {
        return isOrgView;
    }

    /**
     * @param isOrgView
     */
    public void setIsOrgView(Integer isOrgView) {
        this.isOrgView = isOrgView;
    }

    /**
     * @return IS_HOT
     */
    public Integer getIsHot() {
        return isHot;
    }

    /**
     * @param isHot
     */
    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    /**
     * @return REMARK
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
}