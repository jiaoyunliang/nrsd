package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.RsdAccountModel;
import com.rsd.domain.RsdRes;

import java.util.List;

/**
 * @author tony
 * @data 2019-03-21
 * @modifyUser
 * @modifyDate
 */
public interface BnzAccountService {

    RsdAccountModel queryAccountByUserName(RsdAccountModel account) throws Exception;

    List<RsdRes> queryUserPermissionRoleByUserId(Long userId) throws Exception;

    int saveAccount(RsdAccountModel model) throws Exception;

    int updateAccount(RsdAccountModel model) throws Exception;

    Page<List> queryAccountList(RsdAccountModel model) throws Exception;
}
