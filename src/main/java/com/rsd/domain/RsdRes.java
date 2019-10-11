package com.rsd.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Table(name = "RSD_RES")
public class RsdRes implements Serializable {


    private static final long serialVersionUID = -5809449973480438607L;
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "RES_NAME")
    private String resName;

    @Column(name = "RES_URL")
    private String resUrl;

    @Column(name = "RES_TYPE")
    private String resType;

    @Column(name = "RES_CODE")
    private String resCode;

    @Column(name = "RES_COMPONENT")
    private String resComponent;

    @Column(name = "SEQ")
    private Integer seq;

    @Column(name = "ATUH_URI")
    private String atuhUri;

    @Column(name = "ORG_FLAG")
    private Integer orgFlag;

    @Column(name = "HOSPITAL_FLAG")
    private Integer hospitalFlag;

    @Column(name = "ADMIN_FLAG")
    private Integer adminFlag;

    @Column(name = "SYS_ID")
    private Integer sysId;

    @Column(name = "PARENT_CODE")
    private String parentCode;


    @Transient
    private List<RsdRes> children;

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
     * @return RES_NAME
     */
    public String getResName() {
        return resName;
    }

    /**
     * @param resName
     */
    public void setResName(String resName) {
        this.resName = resName;
    }

    /**
     * @return RES_URL
     */
    public String getResUrl() {
        return resUrl;
    }

    /**
     * @param resUrl
     */
    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    /**
     * @return RES_TYPE
     */
    public String getResType() {
        return resType;
    }

    /**
     * @param resType
     */
    public void setResType(String resType) {
        this.resType = resType;
    }

    /**
     * @return RES_CODE
     */
    public String getResCode() {
        return resCode;
    }

    /**
     * @param resCode
     */
    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    /**
     * @return RES_COMPONENT
     */
    public String getResComponent() {
        return resComponent;
    }

    /**
     * @param resComponent
     */
    public void setResComponent(String resComponent) {
        this.resComponent = resComponent;
    }

    /**
     * @return SEQ
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * @return ATUH_URI
     */
    public String getAtuhUri() {
        return atuhUri;
    }

    /**
     * @param atuhUri
     */
    public void setAtuhUri(String atuhUri) {
        this.atuhUri = atuhUri;
    }

    /**
     * @return ORG_FLAG
     */
    public Integer getOrgFlag() {
        return orgFlag;
    }

    /**
     * @param orgFlag
     */
    public void setOrgFlag(Integer orgFlag) {
        this.orgFlag = orgFlag;
    }

    /**
     * @return HOSPITAL_FLAG
     */
    public Integer getHospitalFlag() {
        return hospitalFlag;
    }

    /**
     * @param hospitalFlag
     */
    public void setHospitalFlag(Integer hospitalFlag) {
        this.hospitalFlag = hospitalFlag;
    }

    /**
     * @return ADMIN_FLAG
     */
    public Integer getAdminFlag() {
        return adminFlag;
    }

    /**
     * @param adminFlag
     */
    public void setAdminFlag(Integer adminFlag) {
        this.adminFlag = adminFlag;
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

    /**
     * @return PARENT_CODE
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * @param parentCode
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public List<RsdRes> getChildren() {
        return children;
    }

    public void setChildren(List<RsdRes> children) {
        this.children = children;
    }
}