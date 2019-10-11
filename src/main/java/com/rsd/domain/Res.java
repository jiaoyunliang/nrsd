package com.rsd.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author tony
 * @data 2019-03-21
 * @modifyUser
 * @modifyDate
 */
public class Res implements Serializable {


    private static final long serialVersionUID = -4741531919172418873L;
    private Long id;

    private String resName;

    private String resUrl;

    private String resType;

    private String resCode;

    private String resComponent;

    private Integer seq;

    private String atuhUri;

    private Integer orgFlag;

    private Integer hospitalFlag;

    private Integer adminFlag;

    private String parentCode;

    private List<Res> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResComponent() {
        return resComponent;
    }

    public void setResComponent(String resComponent) {
        this.resComponent = resComponent;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getAtuhUri() {
        return atuhUri;
    }

    public void setAtuhUri(String atuhUri) {
        this.atuhUri = atuhUri;
    }

    public Integer getOrgFlag() {
        return orgFlag;
    }

    public void setOrgFlag(Integer orgFlag) {
        this.orgFlag = orgFlag;
    }

    public Integer getHospitalFlag() {
        return hospitalFlag;
    }

    public void setHospitalFlag(Integer hospitalFlag) {
        this.hospitalFlag = hospitalFlag;
    }

    public Integer getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(Integer adminFlag) {
        this.adminFlag = adminFlag;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public List<Res> getChildren() {
        return children;
    }

    public void setChildren(List<Res> children) {
        this.children = children;
    }


}
