package com.rsd.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "BNZ_MSG_REPLY")
public class BnzMsgReply implements Serializable {


    private static final long serialVersionUID = 6668539614209209955L;
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "MSG_ID")
    private Long msgId;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "REPLY_TIME")
    private Date replyTime;

    @Column(name = "REPLY_USER")
    private String replyUser;

    @Column(name = "REPLY_CONTENT")
    private String replyContent;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return MSG_ID
     */
    public Long getMsgId() {
        return msgId;
    }

    /**
     * @param msgId
     */
    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    /**
     * @return ORG_ID
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return REPLY_TIME
     */
    public Date getReplyTime() {
        return replyTime;
    }

    /**
     * @param replyTime
     */
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    /**
     * @return REPLY_USER
     */
    public String getReplyUser() {
        return replyUser;
    }

    /**
     * @param replyUser
     */
    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }

    /**
     * @return REPLY_CONTENT
     */
    public String getReplyContent() {
        return replyContent;
    }

    /**
     * @param replyContent
     */
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}