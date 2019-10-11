package com.rsd.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @ClassName hdx
 * @Description 产品与资质关系表
 * @Date 2019/5/9 13:41
 * @Version 1.0
 **/
public class BnzQuaProduct {
    private Long id;

    private Long orgQuaInfoId;

    private Long proQuaTypeId;

    private Integer orgProId;

    private Integer isDel;

    private List<Long> quaIds;

    private String updateUser;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date updateTime;

    private String createUser;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date createTime;

    /**
     * 资质类型名称
     */
    private String proQuaTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgQuaInfoId() {
        return orgQuaInfoId;
    }

    public void setOrgQuaInfoId(Long orgQuaInfoId) {
        this.orgQuaInfoId = orgQuaInfoId;
    }

    public Long getProQuaTypeId() {
        return proQuaTypeId;
    }

    public void setProQuaTypeId(Long proQuaTypeId) {
        this.proQuaTypeId = proQuaTypeId;
    }

    public Integer getOrgProId() {
        return orgProId;
    }

    public void setOrgProId(Integer orgProId) {
        this.orgProId = orgProId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public List<Long> getQuaIds() {
        return quaIds;
    }

    public void setQuaIds(List<Long> quaIds) {
        this.quaIds = quaIds;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProQuaTypeName() {
        return proQuaTypeName;
    }

    public void setProQuaTypeName(String proQuaTypeName) {
        this.proQuaTypeName = proQuaTypeName;
    }
}
