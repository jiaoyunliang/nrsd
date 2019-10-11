package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.BnzInsideMsg;
import com.rsd.domain.BnzInsideMsgModel;
import com.rsd.domain.BnzMsgReply;
import com.rsd.domain.BnzMsgReplyModel;
import com.rsd.mapper.BnzInsideMsgMapper;
import com.rsd.mapper.BnzMsgReplyMapper;
import com.rsd.service.BnzInsideMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-06
 * @modifyUser
 * @modifyDate
 */

@Service("bnzInsideMsgService")
public class BnzInsideMsgServiceImpl implements BnzInsideMsgService {

    @Autowired
    private BnzInsideMsgMapper bnzInsideMsgMapper;

    @Autowired
    private BnzMsgReplyMapper bnzMsgReplyMapper;

    @Override
    public Page<List> queryMsgPage(BnzInsideMsgModel model) {
        Page<List> page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzInsideMsgMapper.queryMsgList(model);
        return page;
    }

    @Override
    public List<BnzMsgReplyModel> queryMsgReplyByMsgId(BnzMsgReply model) {
        return bnzMsgReplyMapper.queryMsgReplyByMsgId(model);
    }

    @Override
    public BnzInsideMsg queryInsideMsgById(Long id) {
        return bnzInsideMsgMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveInsideMsg(BnzInsideMsgModel insideMsgModel) {

        insideMsgModel.setId(bnzInsideMsgMapper.getId());

        int tmp = bnzInsideMsgMapper.insertSelective(insideMsgModel);

        List<Long> orgIds = insideMsgModel.getOrgIds();

        if(tmp>0){
            if (!orgIds.isEmpty()){
                for (Long orgId:orgIds){
                    BnzMsgReply  reply = new BnzMsgReply();
                    reply.setId(bnzMsgReplyMapper.getId());
                    reply.setMsgId(insideMsgModel.getId());
                    reply.setOrgId(orgId);
                    bnzMsgReplyMapper.insertSelective(reply);
                }
            }
        }
        return tmp;
    }

    @Override
    public Page<List> queryMsgByOrgPage(BnzInsideMsgModel model) throws Exception {
        Page<List> page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzInsideMsgMapper.queryMsgByOrgList(model);
        return page;
    }

    @Override
    public BnzMsgReplyModel queryMsgReply(BnzMsgReply model) throws Exception {
        return bnzMsgReplyMapper.queryMsgReply(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMsgReply(BnzMsgReply model) throws Exception {
        BnzInsideMsg param = new BnzInsideMsg();
        param.setIsReply(1);
        param.setId(model.getMsgId());
        bnzInsideMsgMapper.updateByPrimaryKeySelective(param);
        return bnzMsgReplyMapper.updateByPrimaryKeySelective(model);
    }
}
