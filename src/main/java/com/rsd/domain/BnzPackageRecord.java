package com.rsd.domain;

import java.util.Date;

/**
 * @author admin
 * @ClassName hdx
 * @Description 打包资质记录表
 * @Date 2019/5/9 14:20
 * @Version 1.0
 **/
public class BnzPackageRecord {

    private Long id;


    private Long orgInfoId;

    /**
     * 打包文件 路径
     */
    private String packageUrl;

    /**
     * 1:企业资质, 2:产品资质, 3:注册证
     */
    private Integer type;

    private Date createTime;

    private String createUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgInfoId() {
        return orgInfoId;
    }

    public void setOrgInfoId(Long orgInfoId) {
        this.orgInfoId = orgInfoId;
    }

    public String getPackageUrl() {
        return packageUrl;
    }

    public void setPackageUrl(String packageUrl) {
        this.packageUrl =   packageUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
