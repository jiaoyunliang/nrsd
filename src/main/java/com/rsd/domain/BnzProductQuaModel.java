package com.rsd.domain;

import com.rsd.utils.PageInput;

import java.io.Serializable;

/**
 * @author admin
 * @ClassName hdx
 * @Description 产品资质
 * @Date 2019/5/6 14:54
 * @Version 1.0
 **/
public class BnzProductQuaModel extends BnzProductQua implements Serializable {
    private static final long serialVersionUID = 4669951502536002371L;
    /**
     * 分页属性
     */
    private PageInput pageInput;
    private RsdDictInfo rsdDictInfo;
    private Integer sum;
    private String quaName;
    private String quaPic;
    private String quaCodeName;//资质编码中文名

    public String getValidState() {
        return validState;
    }

    public void setValidState(String validState) {
        this.validState = validState;
    }

    private String validState;  //1为资质过期，0未过期
    public String getQuaPic() {
        return quaPic;
    }

    public void setQuaPic(String quaPic) {
        this.quaPic = quaPic;
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

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getQuaName() {
        return quaName;
    }

    public void setQuaName(String quaName) {
        this.quaName = quaName;
    }

    public String getQuaCodeName() {
        return quaCodeName;
    }

    public void setQuaCodeName(String quaCodeName) {
        this.quaCodeName = quaCodeName;
    }
}
