package com.rsd.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "BNZ_PROJECT_INFO")
public class BnzProjectInfo implements Serializable {

    private static final long serialVersionUID= 441847901511058798L;
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Column(name = "PROJECT_TIME")
    private Date projectTime;

    @Column(name = "PROJECT_TYPE")
    private String projectType;

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
     * @return PROJECT_NAME
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return PROJECT_TIME
     */
    public Date getProjectTime() {
        return projectTime;
    }

    /**
     * @param projectTime
     */
    public void setProjectTime(Date projectTime) {
        this.projectTime = projectTime;
    }

    /**
     * @return PROJECT_TYPE
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * @param projectType
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
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
}