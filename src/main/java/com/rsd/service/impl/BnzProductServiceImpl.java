package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.*;
import com.rsd.mapper.BnzFileRelationMapper;
import com.rsd.mapper.BnzProductMapper;
import com.rsd.mapper.BnzProductPriceMapper;
import com.rsd.service.BnzCollectionsService;
import com.rsd.service.BnzFileRelationService;
import com.rsd.service.BnzProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-13
 * @modifyUser
 * @modifyDate
 */
@Service("bnzProductService")
public class BnzProductServiceImpl implements BnzProductService {

    @Autowired
    private BnzProductMapper bnzProductMapper;

    @Autowired
    private BnzProductPriceMapper bnzProductPriceMapper;


    @Autowired
    private BnzFileRelationService bnzFileRelationService;

    @Autowired
    private BnzCollectionsService bnzCollectionsService;


    @Autowired
    private BnzFileRelationMapper bnzFileRelationMapper;


    @Override
    public Page<List> queryProductPage(BnzProductModel model) {
        Page<List> page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzProductMapper.queryProductList(model);
        return page;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveProduct(BnzProductModel model) throws Exception {
        model.setId(bnzProductMapper.getId());
        int tmp = bnzProductMapper.insertSelective(model);

        if(!model.getPriceList().isEmpty()){
            for (BnzProductPrice price : model.getPriceList()){
                price.setProductId(model.getId());
                price.setId(bnzProductPriceMapper.getId());
                bnzProductPriceMapper.insertSelective(price);
            }
        }

        if (model.getVideo()!=null){
            model.getVideo().setCategory(6L);
            model.getVideo().setCategoryId(model.getId());
            bnzFileRelationMapper.insertModel(model.getVideo());
        }

        if (!model.getPicList().isEmpty()){
            for (BnzFileRelation pic : model.getPicList()){
                pic.setCategory(5L);
                pic.setCategoryId(model.getId());
                bnzFileRelationMapper.insertModel(pic);
            }
        }

        return tmp;
    }

    @Override
    public int updateProduct(BnzProductModel model) throws Exception {

        BnzProduct target = new BnzProduct();
        BeanUtils.copyProperties(model, target);

        int tmp = bnzProductMapper.updateByPrimaryKeySelective(target);

        BnzProductPrice priceParam = new BnzProductPrice();
        priceParam.setProductId(model.getId());
        bnzProductPriceMapper.delete(priceParam);

        if(!model.getPriceList().isEmpty()){
            for (BnzProductPrice price : model.getPriceList()){
                price.setProductId(model.getId());
                price.setId(bnzProductPriceMapper.getId());
                bnzProductPriceMapper.insertSelective(price);
            }
        }

        bnzFileRelationMapper.deleteFileByCategoryId(model.getId(),6);
        if (model.getVideo()!=null){
            model.getVideo().setCategory(6L);
            model.getVideo().setCategoryId(model.getId());
            bnzFileRelationMapper.insertModel(model.getVideo());
        }

        bnzFileRelationMapper.deleteFileByCategoryId(model.getId(),5);
        if (!model.getPicList().isEmpty()){
            for (BnzFileRelation pic : model.getPicList()){
                pic.setCategory(5L);
                pic.setCategoryId(model.getId());
                bnzFileRelationMapper.insertModel(pic);
            }
        }

        return tmp;
    }

    @Override
    public int updateProduct(BnzProduct product) {
        return bnzProductMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public List<BnzProductPrice> queryProductPriceByProductId(Long productId) {
        BnzProductPrice param = new BnzProductPrice();
        param.setProductId(productId);
        return bnzProductPriceMapper.select(param);
    }

    @Override
    public Page selectProductListPage(BnzProductModel model) {
        Page page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzProductMapper.selectProductList(model);
        List<BnzProductModel> list = page.getResult();
        try {
            if (!list.isEmpty()) {
                for (BnzProductModel p : list) {
                    List<BnzFileRelation> picList = bnzFileRelationService.searchFileByCategoryId(5, p.getId());
                    p.setPicList(picList);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return page;
    }

    @Override
    public Page<List> selectProductPage(BnzProductModel model) {
        Page page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzProductMapper.queryProductList(model);
        List<BnzProductModel> list = page.getResult();

        try {
            if (!list.isEmpty()) {
                for (BnzProductModel p : list) {
                    List<BnzFileRelation> picList = bnzFileRelationService.searchFileByCategoryId(5, p.getId());
                    p.setPicList(picList);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return page;
    }

    @Override
    public BnzProductModel selectProduct(BnzProductModel model) {

        BnzProductModel target = bnzProductMapper.queryProductById(model.getId());

        if (target != null) {

            //ProductPrice
            List<BnzProductPrice> priceList = this.queryProductPriceByProductId(model.getId());
            if (!priceList.isEmpty()) {
                target.setPriceList(priceList);
            }

            if (model.getOrgId() != null) {

                try {
                    BnzCollections param = new BnzCollections();
                    //关注产品
                    param.setCategory(1);
                    param.setCategoryId(target.getId());
                    param.setOrgId(model.getOrgId());
                    BnzCollections followed = bnzCollectionsService.queryCollection(param);
                    target.setFollowed(followed);

                    //使用中产品
                    param.setCategory(2);
                    BnzCollections using = bnzCollectionsService.queryCollection(param);
                    target.setUsing(using);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }

            try {
                //产品图片列表
                List<BnzFileRelation> picList = bnzFileRelationService.searchFileByCategoryId(5, target.getId());
                if (!picList.isEmpty()) {
                    target.setPicList(picList);
                }

                //产品视频
                List<BnzFileRelation> tmpList = bnzFileRelationService.searchFileByCategoryId(6, target.getId());
                if (!tmpList.isEmpty()) {
                    target.setVideo(tmpList.get(0));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return target;

        } else {
            return target;
        }
    }
}
