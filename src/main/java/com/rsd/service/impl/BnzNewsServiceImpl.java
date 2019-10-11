package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.BnzNews;
import com.rsd.domain.BnzNewsModel;
import com.rsd.mapper.BnzNewsMapper;
import com.rsd.service.BnzNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-29
 * @modifyUser
 * @modifyDate
 */
@Service("bnzNewsService")
public class BnzNewsServiceImpl implements BnzNewsService {

    @Autowired
    private BnzNewsMapper bnzNewsMapper;

    @Override
    public int saveNews(BnzNews news) {
        news.setId(bnzNewsMapper.getId());
        return bnzNewsMapper.insertSelective(news);
    }

    @Override
    public int updateNews(BnzNews news) {
        return bnzNewsMapper.updateByPrimaryKeySelective(news);
    }

    @Override
    public Page<List> queryNewsList(BnzNewsModel model) {
        Page<List> page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzNewsMapper.queryNewsList(model);
        return page;
    }

    @Override
    public BnzNews queryNewsById(Long id) {
        return bnzNewsMapper.selectByPrimaryKey(id);
    }
}
