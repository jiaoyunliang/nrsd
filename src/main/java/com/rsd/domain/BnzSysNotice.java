package com.rsd.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "BNZ_SYS_NOTICE")
public class BnzSysNotice implements Serializable {


    private static final long serialVersionUID = -2080265644354611278L;
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SYS_ID")
    private Integer sysId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "IS_RED")
    private Integer isRed;

    @Column(name = "IS_TOP")
    private Integer isTop;

    @Column(name = "IS_NEW")
    private Integer isNew;

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

    @Column(name = "CONTENT")
    private String content;

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
     * @return TITLE
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return TYPE
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
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
     * @return IS_RED
     */
    public Integer getIsRed() {
        return isRed;
    }

    /**
     * @param isRed
     */
    public void setIsRed(Integer isRed) {
        this.isRed = isRed;
    }

    /**
     * @return IS_TOP
     */
    public Integer getIsTop() {
        return isTop;
    }

    /**
     * @param isTop
     */
    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    /**
     * @return IS_NEW
     */
    public Integer getIsNew() {
        return isNew;
    }

    /**
     * @param isNew
     */
    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
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

    /**
     * @return CONTENT
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}