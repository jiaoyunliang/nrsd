package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.RsdRole;
import com.rsd.domain.RsdRoleModel;
import com.rsd.domain.RsdRoleRes;
import com.rsd.mapper.BnzRoleMapper;
import com.rsd.mapper.BnzRoleResMapper;
import com.rsd.service.BnzRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tony
 * @data 2019-04-01
 * @modifyUser
 * @modifyDate
 */
@Service("bnzRoleService")
public class BnzRoleServiceImpl implements BnzRoleService {

    @Autowired
    private BnzRoleMapper bnzRoleMapper;

    @Autowired
    private BnzRoleResMapper bnzRoleResMapper;

    @Override
    public Page<List> queryRoleList(RsdRoleModel role) {

        Page<List> page = PageHelper.startPage(role.getPageInput().getCurrent(), role.getPageInput().getSize());

        bnzRoleMapper.queryRoleList(role);
        return page;
    }


    @Override
    public List<RsdRoleModel> queryRoleListBySysId(RsdRoleModel role) {
        return bnzRoleMapper.queryRoleList(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveRole(RsdRoleModel role) {

        if(role.getId()!=null){

            RsdRoleRes rr = new RsdRoleRes();
            rr.setRoleId(role.getId());
            bnzRoleResMapper.delete(rr);

            for(Long resId : role.getResIds()){
                rr = new RsdRoleRes();
                rr.setRoleId(role.getId());
                rr.setResId(resId);
                bnzRoleResMapper.insert(rr);
            }

            return bnzRoleMapper.updateByPrimaryKeySelective(role);
        } else {
            role.setId(bnzRoleMapper.getId());
            int tmp = bnzRoleMapper.insertSelective(role);

            for(Long resId : role.getResIds()){
                RsdRoleRes rr = new RsdRoleRes();
                rr.setRoleId(role.getId());
                rr.setResId(resId);
                bnzRoleResMapper.insert(rr);
            }

            return tmp;
        }
    }

    @Override
    public int updateRole(RsdRole role) {
        return bnzRoleMapper.updateByPrimaryKeySelective(role);
    }
}
