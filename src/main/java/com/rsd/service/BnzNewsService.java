package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzNews;
import com.rsd.domain.BnzNewsModel;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-29
 * @modifyUser
 * @modifyDate
 */
public interface BnzNewsService {

    int saveNews(BnzNews news) throws Exception;

    int updateNews(BnzNews news) throws Exception;

    Page<List> queryNewsList(BnzNewsModel model) throws Exception;

    BnzNews queryNewsById(Long id) throws Exception;

}
