package com.rsd.service.impl;

import com.rsd.domain.BnzAd;
import com.rsd.mapper.BnzAdMapper;
import com.rsd.service.BnzAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-07
 * @modifyUser
 * @modifyDate
 */
@Service("bnzAdService")
public class BnzAdServiceImpl implements BnzAdService {

    @Autowired
    private BnzAdMapper bnzAdMapper;

    @Override
    public List<BnzAd> queryAdList(BnzAd ad) {
        return bnzAdMapper.queryAdList(ad);
    }

    @Override
    public int updateAd(BnzAd ad) {
        return bnzAdMapper.updateByPrimaryKeySelective(ad);
    }
}
