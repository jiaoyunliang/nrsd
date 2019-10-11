package com.rsd.domain;

import com.rsd.utils.PageInput;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author admin
 * @ClassName hdx
 * @Description 企业资质bean
 * @Date 2019/5/6 15:48
 * @Version 1.0
 **/
public class BnzInstitutionQuaModel extends BnzInstitutionQua implements Serializable {
    private static final long serialVersionUID = 4623689365893900035L;
    /**
     * 分页属性
     */
    private PageInput pageInput;
    private RsdDictInfo rsdDictInfo;
    private ArrayList<BnzFileRelation> bnzFileRelation;
    private Long sum;
    private String orgInfoType;
    private String dictCategoryId;
    private String quaTypeName;//资质类型字典中文名
    private Long category;
    private String quaPic;
    private String validState;  //1为资质过期，0未过期

    public String getValidState() {
        return validState;
    }

    public void setValidState(String validState) {
        this.validState = validState;
    }

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }

    public RsdDictInfo getRsdDictInfo() {
        return rsdDictInfo;
    }

    public void setRsdDictInfo(RsdDictInfo rsdDictInfo) {
        this.rsdDictInfo = rsdDictInfo;
    }

    public ArrayList<BnzFileRelation> getBnzFileRelation() {
        return bnzFileRelation;
    }

    public void setBnzFileRelation(ArrayList<BnzFileRelation> bnzFileRelation) {
        this.bnzFileRelation = bnzFileRelation;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getOrgInfoType() {
        return orgInfoType;
    }

    public void setOrgInfoType(String orgInfoType) {
        this.orgInfoType = orgInfoType;
    }

    public String getDictCategoryId() {
        return dictCategoryId;
    }

    public void setDictCategoryId(String dictCategoryId) {
        this.dictCategoryId = dictCategoryId;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getQuaPic() {
        return quaPic;
    }

    public void setQuaPic(String quaPic) {
        this.quaPic = quaPic;
    }

    public String getQuaTypeName() {
        return quaTypeName;
    }

    public void setQuaTypeName(String quaTypeName) {
        this.quaTypeName = quaTypeName;
    }
}
