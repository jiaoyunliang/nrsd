package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.BnzFeedback;
import com.rsd.domain.BnzFeedbackModel;
import com.rsd.mapper.BnzFeedbackMapper;
import com.rsd.service.BnzFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-06
 * @modifyUser
 * @modifyDate
 */
@Service("bnzFeedbackService")
public class BnzFeedbackServiceImpl implements BnzFeedbackService {

    @Autowired
    private BnzFeedbackMapper bnzFeedbackMapper;

    @Override
    public Page<List> queryFeedbackPage(BnzFeedbackModel model) {
        Page<List> page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());

        bnzFeedbackMapper.queryFeedbackList(model);

        return page;
    }

    @Override
    public int updateFeedback(BnzFeedback model) {
        return bnzFeedbackMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public int saveFeedback(BnzFeedback model) {
        model.setId(bnzFeedbackMapper.getId());
        return bnzFeedbackMapper.insertSelective(model);
    }
}
