package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.*;
import com.rsd.mapper.BnzCollectionsMapper;
import com.rsd.mapper.BnzFileRelationMapper;
import com.rsd.service.BnzCollectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tony
 * @data 2019-06-11
 * @modifyUser
 * @modifyDate
 */
@Service("bnzCollectionsService")
public class BnzCollectionsServiceImpl implements BnzCollectionsService {

    @Autowired
    private BnzCollectionsMapper bnzCollectionsMapper;

    @Autowired
    private BnzFileRelationMapper bnzFileRelationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveCollection(BnzCollections collections) {

        BnzCollections param = new BnzCollections();

        param.setOrgId(collections.getOrgId());
        param.setCategory(collections.getCategory());
        param.setCategoryId(collections.getCategoryId());


        int count = bnzCollectionsMapper.selectCount(param);

        if(count<=0){
            collections.setId(bnzCollectionsMapper.getId());
            return bnzCollectionsMapper.insertSelective(collections);
        } else {
            return count;
        }

    }

    @Override
    public int delCollection(BnzCollections collections) {
        return bnzCollectionsMapper.delete(collections);
    }

    @Override
    public BnzCollections queryCollection(BnzCollections collections) {
        return bnzCollectionsMapper.selectOne(collections);
    }


    @Override
    public Page<List> queryCollectionOrgPage(BnzCollectionsModel model) {
        Page page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzCollectionsMapper.queryCollectionOrgPage(model);
        List<BnzOrgDetailModel> list = page.getResult();

        try {

            if (!list.isEmpty()){
                for (BnzOrgDetailModel orgDetailModel: list){
                    List<BnzFileRelation> logoList = bnzFileRelationMapper.searchFileByCategoryId(3,orgDetailModel.getOrgId());
                    if(!logoList.isEmpty()){
                        orgDetailModel.setOrgPic(logoList.get(0));
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return page;
    }

    @Override
    public Page<List> queryCollectionProductPage(BnzCollectionsModel model) {
        Page page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzCollectionsMapper.queryCollectionProductPage(model);
        List<BnzProductModel> list = page.getResult();
        try {
            if (!list.isEmpty()){
                for (BnzProductModel p: list){
                    List<BnzFileRelation> picList = bnzFileRelationMapper.searchFileByCategoryId(5,p.getId());
                    p.setPicList(picList);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return page;
    }
}
