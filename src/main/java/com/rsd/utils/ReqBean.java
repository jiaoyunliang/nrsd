package com.rsd.utils;

import java.io.Serializable;

/**
 * @author tony
 * @data 2019-03-19
 * @modifyUser
 * @modifyDate
 */
public class ReqBean<T> implements Serializable {


    private static final long serialVersionUID = -6228815014522796320L;
    private String token;
    private T param;


    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
