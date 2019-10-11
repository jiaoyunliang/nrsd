package com.rsd.service;

import com.rsd.domain.RsdDictInfo;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-23
 * @modifyUser
 * @modifyDate
 */
public interface BnzDictInfoService {


    List<RsdDictInfo> queryDictByCategoryId(RsdDictInfo model);
}
