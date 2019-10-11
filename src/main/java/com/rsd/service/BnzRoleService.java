package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.RsdRole;
import com.rsd.domain.RsdRoleModel;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-01
 * @modifyUser
 * @modifyDate
 */
public interface BnzRoleService {

    Page<List> queryRoleList(RsdRoleModel role) throws Exception;

    List<RsdRoleModel> queryRoleListBySysId(RsdRoleModel role) throws Exception;

    int saveRole(RsdRoleModel role) throws Exception;

    int updateRole(RsdRole role) throws Exception;
}
