package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzProductType;
import com.rsd.domain.BnzProductTypeModel;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-22
 * @modifyUser
 * @modifyDate
 */
public interface BnzProductTypeService {

    int saveProductType(BnzProductType productType) throws Exception;

    int updateProductType(BnzProductType productType) throws Exception;

    Page<List> queryProductType(BnzProductTypeModel param) throws Exception;

    List<BnzProductTypeModel> queryProductType() throws Exception;

    List queryOrgType() throws Exception;

    /**
     * 更新产品类型点击数
     * @param id
     * @return
     * @throws Exception
     */
    int updateProductTypeClickNum(Long id) throws Exception;
}
