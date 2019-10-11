package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.BnzFileRelation;
import com.rsd.domain.BnzOrgDetail;
import com.rsd.domain.BnzOrgDetailModel;
import com.rsd.mapper.BnzFileRelationMapper;
import com.rsd.mapper.BnzOrgDetailMapper;
import com.rsd.service.BnzFileRelationService;
import com.rsd.service.BnzOrgDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-05
 * @modifyUser
 * @modifyDate
 */
@Service("bnzOrgDetailService")
public class BnzOrgDetailServiceImpl implements BnzOrgDetailService {

    @Autowired
    private BnzOrgDetailMapper bnzOrgDetailMapper;

    @Autowired
    private BnzFileRelationMapper bnzFileRelationMapper;

    @Autowired
    private BnzFileRelationService bnzFileRelationService;

    @Override
    public Page queryOrgDetailPage(BnzOrgDetailModel model) {
        Page page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzOrgDetailMapper.queryOrgDetailList(model);
        return page;
    }


    @Override
    public BnzOrgDetailModel queryOrgDetailById(BnzOrgDetailModel model) throws Exception {
        BnzOrgDetailModel detailModel = bnzOrgDetailMapper.queryOrgDetailById(model);

        if(null!=detailModel){
            //企业图片
            List<BnzFileRelation> logoList = bnzFileRelationService.searchFileByCategoryId(3,detailModel.getOrgId());
            if (!logoList.isEmpty()){
                detailModel.setOrgPic(logoList.get(0));
            }

            //企业 品牌LOGO
            List<BnzFileRelation> brandPics = bnzFileRelationService.searchFileByCategoryId(4,detailModel.getOrgId());
            if(!brandPics.isEmpty()){
                detailModel.setBrandLogo(brandPics);
            }
        }

        return detailModel;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrgDetail(BnzOrgDetailModel model) throws Exception {


        BnzOrgDetail target = new BnzOrgDetail();
        BeanUtils.copyProperties(model, target);

        int tmp = bnzOrgDetailMapper.insertSelective(target);

        //企业图片
        if(model.getOrgPic()!=null){
            model.getOrgPic().setCategory(3L);
            model.getOrgPic().setCategoryId(target.getOrgId());
            bnzFileRelationMapper.insertModel(model.getOrgPic());
        }

        //企业 品牌LOGO
        if(!model.getBrandLogo().isEmpty()){
            for (BnzFileRelation logo : model.getBrandLogo()){
                logo.setCategory(4L);
                logo.setCategoryId(target.getOrgId());
                bnzFileRelationMapper.insertModel(logo);
            }
        }

        return tmp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOrgDetail(BnzOrgDetailModel model) throws Exception {

        BnzOrgDetail target = new BnzOrgDetail();
        BeanUtils.copyProperties(model, target);

        int tmp = bnzOrgDetailMapper.updateByPrimaryKeySelective(target);

        //企业图片
        bnzFileRelationMapper.deleteFileByCategoryId(model.getOrgId(),3);
        if(model.getOrgPic()!=null){
            model.getOrgPic().setCategory(3L);
            model.getOrgPic().setCategoryId(target.getOrgId());
            bnzFileRelationMapper.insertModel(model.getOrgPic());
        }

        //企业 品牌LOGO
        bnzFileRelationMapper.deleteFileByCategoryId(model.getOrgId(),4);
        if(!model.getBrandLogo().isEmpty()){
            for (BnzFileRelation logo : model.getBrandLogo()){
                logo.setCategory(4L);
                logo.setCategoryId(target.getOrgId());
                bnzFileRelationMapper.insertModel(logo);
            }
        }
        return tmp;
    }
}
