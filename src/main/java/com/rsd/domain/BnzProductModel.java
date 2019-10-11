package com.rsd.domain;

import com.rsd.utils.PageInput;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-13
 * @modifyUser
 * @modifyDate
 */
public class BnzProductModel extends BnzProduct {


    private static final long serialVersionUID = -3631368106487173508L;

    /**
     * 分页属性
     * */
    private PageInput pageInput;

    private String orgName;

    private String productTypeName;

    /**
     * 产品图片
     */
    private List<BnzFileRelation> picList;

    /**
     * 产品视频
     */
    private BnzFileRelation video;

    /**
     * 关注
     */
    private BnzCollections followed;

    /**
     * 使用
     */
    private BnzCollections using;


    private List<BnzProductPrice> priceList;


    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }


    public List<BnzFileRelation> getPicList() {
        return picList;
    }

    public void setPicList(List<BnzFileRelation> picList) {
        this.picList = picList;
    }

    public BnzFileRelation getVideo() {
        return video;
    }

    public void setVideo(BnzFileRelation video) {
        this.video = video;
    }

    public BnzCollections getFollowed() {
        return followed;
    }

    public void setFollowed(BnzCollections followed) {
        this.followed = followed;
    }

    public BnzCollections getUsing() {
        return using;
    }

    public void setUsing(BnzCollections using) {
        this.using = using;
    }

    public List<BnzProductPrice> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<BnzProductPrice> priceList) {
        this.priceList = priceList;
    }
}
