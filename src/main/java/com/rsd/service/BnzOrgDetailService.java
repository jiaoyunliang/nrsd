package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzOrgDetailModel;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-05
 * @modifyUser
 * @modifyDate
 */
public interface BnzOrgDetailService {

    Page<List> queryOrgDetailPage(BnzOrgDetailModel model) throws Exception;

    BnzOrgDetailModel queryOrgDetailById(BnzOrgDetailModel model) throws Exception;

    /**
     * 企业端 添加 黄页
     * @param model
     * @return
     * @throws Exception
     */
    int saveOrgDetail(BnzOrgDetailModel model) throws Exception;

    /**
     * 企业端 修改 黄页
     * @param model
     * @return
     * @throws Exception
     */
    int updateOrgDetail(BnzOrgDetailModel model) throws Exception;

}
