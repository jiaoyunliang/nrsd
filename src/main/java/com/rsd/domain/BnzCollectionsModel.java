package com.rsd.domain;

import com.rsd.utils.PageInput;

/**
 * @author tony
 * @data 2019-06-13
 * @modifyUser
 * @modifyDate
 */
public class BnzCollectionsModel extends BnzCollections{


    private static final long serialVersionUID = -3138154485976784730L;

    private String productName;

    private String orgName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 分页属性
     * */
    private PageInput pageInput;

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }

}
