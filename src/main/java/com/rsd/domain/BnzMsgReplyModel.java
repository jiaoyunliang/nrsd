package com.rsd.domain;

/**
 * @author tony
 * @data 2019-05-06
 * @modifyUser
 * @modifyDate
 */
public class BnzMsgReplyModel extends BnzMsgReply {


    private static final long serialVersionUID = -8454253212969964558L;

    private String orgName;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
