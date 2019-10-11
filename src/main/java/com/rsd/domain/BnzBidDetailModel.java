package com.rsd.domain;

import com.rsd.utils.PageInput;

public class BnzBidDetailModel extends BnzBidDetail{

    /**
     * 分页属性
     */
    private PageInput pageInput;

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public PageInput getPageInput() {
        return pageInput;
    }

    public void setPageInput(PageInput pageInput) {
        this.pageInput = pageInput;
    }
}
