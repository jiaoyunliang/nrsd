package com.rsd.domain;

import com.rsd.utils.PageInput;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-05
 * @modifyUser
 * @modifyDate
 */
public class BnzOrgDetailModel extends BnzOrgDetail {


    private static final long serialVersionUID = 6747276182717697837L;
    /**
     * 分页属性
     * */
    private PageInput pageInput;

    private String orgName;


    /**
     * 收藏
     */
    private BnzCollections bnzCollections;

    /**
     * 企业图片
     */
    private BnzFileRelation orgPic;

    /**
     * 品牌 logo
     */
    private List<BnzFileRelation> brandLogo;

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

    public BnzFileRelation getOrgPic() {
        return orgPic;
    }

    public void setOrgPic(BnzFileRelation orgPic) {
        this.orgPic = orgPic;
    }

    public List<BnzFileRelation> getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(List<BnzFileRelation> brandLogo) {
        this.brandLogo = brandLogo;
    }

    public BnzCollections getBnzCollections() {
        return bnzCollections;
    }

    public void setBnzCollections(BnzCollections bnzCollections) {
        this.bnzCollections = bnzCollections;
    }
}
