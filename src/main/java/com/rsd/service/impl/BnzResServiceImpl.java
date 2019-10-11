package com.rsd.service.impl;

import com.rsd.domain.RsdRes;
import com.rsd.domain.RsdRoleModel;
import com.rsd.mapper.RsdResMapper;
import com.rsd.service.BnzResService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tony
 * @data 2019-03-27
 * @modifyUser
 * @modifyDate
 */
@Service("bnzResService")
public class BnzResServiceImpl implements BnzResService {

    private static final Logger logger = LoggerFactory.getLogger(BnzResServiceImpl.class);

    @Autowired
    private RsdResMapper rsdResMapper;

    @Override
    public List<RsdRes> queryResListByRoleId(RsdRoleModel model) {
        return rsdResMapper.queryResListByRoleId(model);
    }

    @Override
    public int saveRes(RsdRes res) {
        res.setId(rsdResMapper.getId());
        return rsdResMapper.insertSelective(res);
    }

    @Override
    public int updateRes(RsdRes res) {
        return rsdResMapper.updateByPrimaryKeySelective(res);
    }

    @Override
    public int deleteById(Long id) throws Exception {
        int flag = 0;
        try{
            flag = rsdResMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return flag;
    }

    @Override
    public List<RsdRes> queryResList(RsdRes res) {
        List<RsdRes> result = new ArrayList<>();
        List<RsdRes> list = rsdResMapper.queryResList(res);

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
