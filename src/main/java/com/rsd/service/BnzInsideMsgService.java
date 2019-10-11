package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzInsideMsg;
import com.rsd.domain.BnzInsideMsgModel;
import com.rsd.domain.BnzMsgReply;
import com.rsd.domain.BnzMsgReplyModel;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-06
 * @modifyUser
 * @modifyDate
 */
public interface BnzInsideMsgService {


    int saveInsideMsg(BnzInsideMsgModel insideMsgModel) throws Exception;

    Page<List> queryMsgPage(BnzInsideMsgModel model) throws Exception;

    List<BnzMsgReplyModel> queryMsgReplyByMsgId(BnzMsgReply model) throws Exception;

    BnzInsideMsg queryInsideMsgById(Long id) throws Exception;


    /**
     * 企业 查站内消息
     * @param model
     * @return
     * @throws Exception
     */
    Page<List> queryMsgByOrgPage(BnzInsideMsgModel model) throws Exception;

    /**
     * 企业 查站内消息回复
     * @param model
     * @return
     * @throws Exception
     */
    BnzMsgReplyModel queryMsgReply(BnzMsgReply model) throws Exception;

    /**
     * 企业 回复站内消息
     * @param model
     * @return
     * @throws Exception
     */
    int updateMsgReply(BnzMsgReply model) throws Exception;
}
