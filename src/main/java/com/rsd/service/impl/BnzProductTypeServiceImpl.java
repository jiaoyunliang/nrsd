package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.BnzProductType;
import com.rsd.domain.BnzProductTypeModel;
import com.rsd.mapper.BnzProductTypeMapper;
import com.rsd.service.BnzProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-23
 * @modifyUser
 * @modifyDate
 */
@Service("bnzProductTypeService")
public class BnzProductTypeServiceImpl implements BnzProductTypeService {

    @Autowired
    private BnzProductTypeMapper bnzProductTypeMapper;

    @Override
    public int saveProductType(BnzProductType productType) {
        productType.setId(bnzProductTypeMapper.getId());
        return bnzProductTypeMapper.insertSelective(productType);
    }

    @Override
    public int updateProductType(BnzProductType productType) {
        return bnzProductTypeMapper.updateByPrimaryKeySelective(productType);
    }

    @Override
    public Page<List> queryProductType(BnzProductTypeModel param) {
        Page<List> page = PageHelper.startPage(param.getPageInput().getCurrent(), param.getPageInput().getSize());
        bnzProductTypeMapper.queryProductType();
        return page;
    }

    @Override
    public List<BnzProductTypeModel> queryProductType() {
        return bnzProductTypeMapper.queryProductType();
    }

    @Override
    public List queryOrgType() {
        return bnzProductTypeMapper.queryProductType();
    }


    @Override
    public int updateProductTypeClickNum(Long id) throws Exception {
        return bnzProductTypeMapper.updateProductTypeClickNum(id);
    }
}
