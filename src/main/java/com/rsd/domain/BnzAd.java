package com.rsd.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "BNZ_AD")
public class BnzAd implements Serializable {


    private static final long serialVersionUID = 7465036518028756804L;
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE")
    private Integer type;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SEQ")
    private Integer seq;

    @Column(name = "SYS_ID")
    private Integer sysId;

    @Column(name = "PIC_PATH")
    private String picPath;

    @Column(name = "AD_URL")
    private String adUrl;

    @Column(name = "STATUS")
    private Integer status;

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
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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
     * @return PIC_PATH
     */
    public String getPicPath() {
        return picPath;
    }

    /**
     * @param picPath
     */
    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    /**
     * @return AD_URL
     */
    public String getAdUrl() {
        return adUrl;
    }

    /**
     * @param adUrl
     */
    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    /**
     * @return STATUS
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}