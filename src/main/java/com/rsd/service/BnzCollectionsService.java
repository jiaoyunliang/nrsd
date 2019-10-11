package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzCollections;
import com.rsd.domain.BnzCollectionsModel;

import java.util.List;

/**
 * @author tony
 * @data 2019-06-11
 * @modifyUser
 * @modifyDate
 */
public interface BnzCollectionsService {

    int saveCollection(BnzCollections collections) throws Exception;

    int delCollection(BnzCollections collections) throws Exception;

    BnzCollections queryCollection(BnzCollections collections) throws Exception;

    /**
     * 医院 关注的企业
     * @param model
     * @return
     */
    Page<List> queryCollectionOrgPage(BnzCollectionsModel model) throws Exception;

    /**
     * 医院 关注和使用的产品
     * @param model
     * @return
     */
    Page<List> queryCollectionProductPage(BnzCollectionsModel model) throws Exception;
}
