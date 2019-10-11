package com.rsd.domain;

import com.rsd.utils.PageInput;

import java.io.Serializable;
import java.util.List;

/**
 * @author admin
 * @ClassName hdx
 * @Description 项目信息model类
 * @Date 2019/4/23 10:32
 * @Version 1.0
 **/
public class BnzProjectInfoModel extends BnzProjectInfo implements Serializable {
    private static final long serialVersionUID = 2044938321047071857L;
    /**
     * 分页属性
     */
    private PageInput pageInput;

    private RsdOrgInfo rsdOrgInfo;

    private List<BnzBidShowColumn> bidShowColumnList;

    public List<BnzBidShowColumn> getBidShowColumnList() {
        return bidShowColumnList;
    }

    public void setBidShowColumnList(List<BnzBidShowColumn> bidShowColumnList) {
        this.bidShowColumnList = bidShowColumnList;
    }

    public RsdOrgInfo getRsdOrgInfo() {
        return rsdOrgInfo;
    }

    public void setRsdOrgInfo(RsdOrgInfo rsdOrgInfo) {
        this.rsdOrgInfo = rsdOrgInfo;
    }

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }
}
