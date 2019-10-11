package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.BnzAccountApply;
import com.rsd.domain.BnzAccountApplyModel;
import com.rsd.mapper.BnzAccountApplyMapper;
import com.rsd.service.BnzAccountApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-23
 * @modifyUser
 * @modifyDate
 */
@Service("bnzAccountApplyService")
public class BnzAccountApplyServiceImpl implements BnzAccountApplyService {

    @Autowired
    private BnzAccountApplyMapper bnzAccountApplyMapper;

    @Override
    public Page<List> queryAccountApply(BnzAccountApplyModel model) {
        Page<List> page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzAccountApplyMapper.queryAccountApply(model);
        return page;
    }

    @Override
    public int saveAccountApply(BnzAccountApply model) {
        model.setId(bnzAccountApplyMapper.getId());
        return bnzAccountApplyMapper.insertSelective(model);
    }

    @Override
    public int updateAccountApply(BnzAccountApply model) {
        return bnzAccountApplyMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public int queryAccountApplyNonReadNum(BnzAccountApply model) {
        return bnzAccountApplyMapper.selectCount(model);
    }
}
