package com.rsd.service;

import com.rsd.domain.BnzAd;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-07
 * @modifyUser
 * @modifyDate
 */
public interface BnzAdService {

    List<BnzAd> queryAdList(BnzAd ad) throws Exception;

    int updateAd(BnzAd ad) throws Exception;

}
