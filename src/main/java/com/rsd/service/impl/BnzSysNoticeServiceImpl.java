package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.BnzNoticeRead;
import com.rsd.domain.BnzSysNotice;
import com.rsd.domain.BnzSysNoticeModel;
import com.rsd.mapper.BnzNoticeReadMapper;
import com.rsd.mapper.BnzSysNoticeMapper;
import com.rsd.service.BnzSysNoticeService;
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
@Service("bnzSysNoticeService")
public class BnzSysNoticeServiceImpl implements BnzSysNoticeService {

    @Autowired
    private BnzSysNoticeMapper bnzSysNoticeMapper;

    @Autowired
    private BnzNoticeReadMapper bnzNoticeReadMapper;

    @Override
    public int saveNotice(BnzSysNotice notice) {
        notice.setId(bnzSysNoticeMapper.getId());
        return bnzSysNoticeMapper.insertSelective(notice);
    }

    @Override
    public int updateNotice(BnzSysNotice notice) {
        return bnzSysNoticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BnzSysNotice queryNoticeById(BnzNoticeRead model) {

        BnzNoticeRead param = new BnzNoticeRead();
        param.setOrgId(model.getOrgId());
        param.setNoticeId(model.getNoticeId());

        int count = bnzNoticeReadMapper.selectCount(param);
        if(count==0){
            model.setId(bnzNoticeReadMapper.getId());
            bnzNoticeReadMapper.insertSelective(model);
        }
        return bnzSysNoticeMapper.selectByPrimaryKey(model.getNoticeId());
    }

    @Override
    public Page<List> querySysNoticePage(BnzSysNoticeModel model) {
        Page<List> page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzSysNoticeMapper.querySysNoticeList(model);
        return page;
    }

    @Override
    public Page<List> queryOrgReadNoticePage(BnzSysNoticeModel model) {
        Page<List> page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzSysNoticeMapper.queryOrgReadNoticeList(model);
        return page;
    }

    @Override
    public int queryNoticeUnreadNum(BnzSysNoticeModel model) {
        return bnzSysNoticeMapper.queryNoticeUnreadNum(model);
    }
}
