package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.*;
import com.rsd.mapper.BnzAccountMapper;
import com.rsd.mapper.BnzOrgInfoMapper;
import com.rsd.mapper.BnzOrgTypesMapper;
import com.rsd.mapper.BnzProductTypeMapper;
import com.rsd.service.BnzOrgInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-02
 * @modifyUser
 * @modifyDate
 */
@Service("bnzOrgInfoService")
public class BnzOrgInfoServiceImpl implements BnzOrgInfoService {

    @Autowired
    private BnzOrgInfoMapper bnzOrgInfoMapper;

    @Autowired
    private BnzOrgTypesMapper bnzOrgTypesMapper;

    @Autowired
    private BnzProductTypeMapper bnzProductTypeMapper;

    @Autowired
    private BnzAccountMapper bnzAccountMapper;


    @Override
    public List<RsdOrgInfo> queryOrgList(RsdOrgInfo model) {
        return bnzOrgInfoMapper.queryOrgList(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrg(RsdOrgInfoModel model) {
        model.setId(bnzOrgInfoMapper.getId());
        int tmp =bnzOrgInfoMapper.insertSelective(model);

        //机构=企业
        if(model.getSysId()==3){
            for(Long typeId : model.getOrgTypeIds()){
                RsdOrgTypes orgTypes = new RsdOrgTypes();
                orgTypes.setOrgId(model.getId());
                orgTypes.setTypeId(typeId);
                bnzOrgTypesMapper.insert(orgTypes);
            }
        }
        return tmp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOrg(RsdOrgInfoModel model) {
        int tmp = bnzOrgInfoMapper.updateByPrimaryKeySelective(model);

        //机构=企业
        if(model.getSysId()==3){
            RsdOrgTypes orgTypes = new RsdOrgTypes();
            orgTypes.setOrgId(model.getId());
            bnzOrgTypesMapper.delete(orgTypes);

            for(Long typeId : model.getOrgTypeIds()){
                orgTypes = new RsdOrgTypes();
                orgTypes.setOrgId(model.getId());
                orgTypes.setTypeId(typeId);
                bnzOrgTypesMapper.insert(orgTypes);
            }
        }

        RsdAccount account = new RsdAccount();
        account.setOrgId(model.getId());
        account.setRoleId(model.getOrgLevel());
        bnzAccountMapper.updateAccountRoleByOrgId(account);

        return tmp;
    }

    @Override
    public Page<List> queryOrgPage(RsdOrgInfoModel model) {
        Page<List> page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzOrgInfoMapper.queryOrgPage(model);
        return page;
    }

    @Override
    public List<BnzProductType> queryOrgTypes(RsdOrgInfoModel model) {
        return bnzProductTypeMapper.queryOrgTypes(model.getId());
    }

    @Override
    public Page<List> queryEnterpriseList(RsdOrgInfoModel model) throws Exception {
        Page<List> page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
        bnzOrgInfoMapper.queryEnterpriseList(model);
        return page;
    }
}
