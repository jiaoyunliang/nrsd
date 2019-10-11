package com.rsd.domain;

/**
 * @author tony
 * @data 2019-06-18
 * @modifyUser
 * @modifyDate
 */
public class PasswordModel {


    private String passwd;

    private String newPasswd;

    private String repeatPasswd;

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }

    public String getRepeatPasswd() {
        return repeatPasswd;
    }

    public void setRepeatPasswd(String repeatPasswd) {
        this.repeatPasswd = repeatPasswd;
    }
}
