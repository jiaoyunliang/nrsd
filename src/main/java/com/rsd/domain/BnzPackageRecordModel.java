package com.rsd.domain;

import com.rsd.utils.PageInput;

import java.io.Serializable;

/**
 * @author admin
 * @ClassName hdx
 * @Description 资质打包类
 * @Date 2019/5/9 14:22
 * @Version 1.0
 **/
public class BnzPackageRecordModel extends BnzPackageRecord implements Serializable {
    private static final long serialVersionUID = -5961280724195400944L;

    private Integer order;
    private String dictId;
    private String dictName;
    private Integer state;
    /**
     * 分页属性
     */
    private PageInput pageInput;

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
