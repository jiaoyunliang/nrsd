package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzAccountApply;
import com.rsd.domain.BnzAccountApplyModel;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-23
 * @modifyUser
 * @modifyDate
 */
public interface BnzAccountApplyService {

    Page<List> queryAccountApply(BnzAccountApplyModel model) throws Exception;

    int saveAccountApply(BnzAccountApply model)  throws Exception;

    int updateAccountApply(BnzAccountApply model)  throws Exception;

    int queryAccountApplyNonReadNum(BnzAccountApply model)  throws Exception;


}
