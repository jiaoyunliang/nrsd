package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.RsdAccountModel;
import com.rsd.domain.RsdOrgInfo;
import com.rsd.domain.RsdRes;
import com.rsd.domain.RsdRole;
import com.rsd.mapper.BnzAccountMapper;
import com.rsd.mapper.BnzOrgInfoMapper;
import com.rsd.mapper.BnzRoleMapper;
import com.rsd.service.BnzAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tony
 * @data 2019-03-21
 * @modifyUser
 * @modifyDate
 */
@Service("bnzAccountService")
public class BnzAccountServiceImpl implements BnzAccountService {

    private static final Logger logger = LoggerFactory.getLogger(BnzAccountServiceImpl.class);

    @Autowired
    private BnzAccountMapper bnzAccountMapper;

    @Autowired
    private BnzRoleMapper bnzRoleMapper;

    @Autowired
    private BnzOrgInfoMapper bnzOrgInfoMapper;

    @Override
    public RsdAccountModel queryAccountByUserName(RsdAccountModel account) {

        RsdAccountModel accountModel = bnzAccountMapper.queryAccountByUserName(account);

        if(null!=accountModel){
            RsdRole role = bnzRoleMapper.selectByPrimaryKey(accountModel.getRoleId());
            accountModel.setRole(role);
            RsdOrgInfo orgInfo = bnzOrgInfoMapper.selectByPrimaryKey(accountModel.getOrgId());
            accountModel.setOrgInfo(orgInfo);
        }
        return accountModel;
    }

    @Override
    public Page<List> queryAccountList(RsdAccountModel model) {

        Page<List> page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());

        bnzAccountMapper.queryAccountList(model);

        return page;
    }


    @Override
    public int saveAccount(RsdAccountModel model) throws Exception{
        try{
            model.setId(bnzAccountMapper.getId());
            return bnzAccountMapper.insertSelective(model);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAccount(RsdAccountModel model) {
        if(model.getSysId()!=null && model.getSysId()==1 && model.getEnabledState()!=null && model.getEnabledState()==1 ){
            bnzAccountMapper.updateAccountIsService();
        } else if(model.getSysId()!=null && model.getSysId()==1 && model.getIsService()!=null && model.getIsService()==1){
            bnzAccountMapper.updateAccountIsService();
        }
        return bnzAccountMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public List<RsdRes> queryUserPermissionRoleByUserId(Long userId) {
        List<RsdRes> result = new ArrayList<>();
        List<RsdRes>  list = bnzAccountMapper.queryUserPermissionRoleByUserId(userId);

        for (RsdRes data0 : list) {
            boolean mark = true;
            for (RsdRes data1 : list) {

                if (data1.getParentCode() != null && data0.getParentCode().equals(data1.getResCode())) {
                    mark = false;
                    if (data1.getChildren() == null) {
                        data1.setChildren(new ArrayList<>());
                    }
                    data1.getChildren().add(data0);
                    break;
                }

            }

            if (mark) {
                result.add(data0);
            }
        }
        return result;
    }
}
