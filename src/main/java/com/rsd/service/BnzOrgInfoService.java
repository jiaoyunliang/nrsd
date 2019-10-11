package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.RsdOrgInfo;
import com.rsd.domain.RsdOrgInfoModel;
import com.rsd.domain.BnzProductType;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-02
 * @modifyUser
 * @modifyDate
 */
public interface BnzOrgInfoService {

    List<RsdOrgInfo> queryOrgList(RsdOrgInfo model) throws Exception;

    int saveOrg(RsdOrgInfoModel model) throws Exception;

    int updateOrg(RsdOrgInfoModel model) throws Exception;

    Page<List> queryOrgPage(RsdOrgInfoModel model) throws Exception;

    List<BnzProductType> queryOrgTypes(RsdOrgInfoModel model) throws Exception;

    /**
     * 企业端 首页 随机
     * @param model
     * @return
     * @throws Exception
     */
    Page<List> queryEnterpriseList(RsdOrgInfoModel model) throws Exception;

}
