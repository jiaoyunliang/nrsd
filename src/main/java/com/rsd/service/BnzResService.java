package com.rsd.service;

import com.rsd.domain.RsdRes;
import com.rsd.domain.RsdRoleModel;

import java.util.List;

/**
 * @author tony
 * @data 2019-03-27
 * @modifyUser
 * @modifyDate
 */
public interface BnzResService {

    int deleteById(Long id) throws Exception;

    List<RsdRes> queryResList(RsdRes res) throws Exception;

    int saveRes(RsdRes res) throws Exception;

    int updateRes(RsdRes res) throws Exception;

    List<RsdRes> queryResListByRoleId(RsdRoleModel model) throws Exception;


}
