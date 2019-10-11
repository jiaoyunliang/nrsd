package com.rsd.utils;


import org.springframework.util.StringUtils;

/**
 * 分页属性
 *
 * @author zhangchao
 */
public class PageInput {


    private int size = 10;
    private int total;
    private int current = 1;
    private String sort = "";


    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {

        if (null != sort && !"".equals(sort)) {
            sort = sort.toUpperCase().replace("ending".toUpperCase(), "");
        }

        return sort;
    }

    public void setSort(String sort) {


        if (!StringUtils.isEmpty(sort)) {

            sort = Const.underscoreName(sort);

            sort = sort.toUpperCase();

            if (sort.indexOf(Const.PageQuery.ORDER_BY) < 0) {
                sort = Const.PageQuery.ORDER_BY + "  " + sort;
            }
        }
        this.sort = sort;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    /**
     * 默认排序，页面排序会覆盖默认排序
     */
    public void setDefaultSort(String... defaultSort) {

        if (!StringUtils.isEmpty(getSort())) {
            return;
        }
        setAppendSort(defaultSort);

    }

    /**
     * 追加排序，始终保持，页面排序＋追加排序
     */
    public void setAppendSort(String... appendSort) {

        String sort = getSort();

        StringBuilder stringBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(sort)) {

            sort = sort.replace(Const.PageQuery.ORDER_BY, "");

            stringBuilder.append(sort);
        }
        if (null != appendSort && appendSort.length > 0) {
            for (String s : appendSort) {
                if (sort.length() != 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(s);
            }
            setSort(stringBuilder.toString());
        }

    }


}
