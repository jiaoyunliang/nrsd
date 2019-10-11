package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzProduct;
import com.rsd.domain.BnzProductModel;
import com.rsd.domain.BnzProductPrice;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-13
 * @modifyUser
 * @modifyDate
 */
public interface BnzProductService {

    /**
     * 企业端
     * @param model
     * @return
     * @throws Exception
     */
    int saveProduct(BnzProductModel model) throws Exception;

    /**
     * 企业端
     * @param model
     * @return
     * @throws Exception
     */
    int updateProduct(BnzProductModel model) throws Exception;


    /**
     * 管理端
     * @param model
     * @return
     */
    Page<List> queryProductPage(BnzProductModel model) throws Exception;

    /**
     * 管理端
     * @param product
     * @return
     * @throws Exception
     */
    int updateProduct(BnzProduct product) throws Exception;


    List<BnzProductPrice> queryProductPriceByProductId(Long productId) throws Exception;

    /**
     * 医院端 首页
     * @param model
     * @return
     */
    Page<List> selectProductListPage(BnzProductModel model) throws Exception;


    /**
     * 医院端
     * @param model
     * @return
     */
    Page<List> selectProductPage(BnzProductModel model) throws Exception;


    /**
     * 医院端 产品详情
     * @param model
     * @return
     */
    BnzProductModel selectProduct(BnzProductModel model) throws Exception;

}
