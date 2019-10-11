package com.rsd.service.impl;

import com.rsd.domain.RsdDictInfo;
import com.rsd.mapper.BnzDictInfoMapper;
import com.rsd.service.BnzDictInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-23
 * @modifyUser
 * @modifyDate
 */
@Service("bnzDictInfoService")
public class BnzDictInfoServiceImpl implements BnzDictInfoService {

    @Autowired
    private BnzDictInfoMapper bnzDictInfoMapper;

    @Override
    public List<RsdDictInfo> queryDictByCategoryId(RsdDictInfo model) {
        return bnzDictInfoMapper.queryDictByCategoryId(model);
    }
}
