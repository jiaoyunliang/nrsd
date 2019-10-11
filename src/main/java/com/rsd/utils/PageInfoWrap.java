package com.rsd.utils;

import com.github.pagehelper.Page;

/**
 * 包装分页使用
 *
 * @author zhangchao
 */
public class PageInfoWrap {

    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 起始行
     */
    private int startRow;
    /**
     * 末行
     */
    private int endRow;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;

    public static PageInfoWrap instance(Page page) {

        PageInfoWrap pageInfoWrap = new PageInfoWrap();
        pageInfoWrap.pageNum = page.getPageNum();
        pageInfoWrap.pageSize = page.getPageSize();
        pageInfoWrap.startRow = page.getStartRow();
        pageInfoWrap.endRow = page.getEndRow();
        pageInfoWrap.total = page.getTotal();
        return pageInfoWrap;
    }

    private PageInfoWrap() {
    }

    public PageInfoWrap(Page page) {
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.startRow = page.getStartRow();
        this.endRow = page.getEndRow();
        this.total = page.getTotal();
    }

    public PageInfoWrap get() {
        return this;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPages() {
        return pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public long getTotal() {
        return total;
    }
}
