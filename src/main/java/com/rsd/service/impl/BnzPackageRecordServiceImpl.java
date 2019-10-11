package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.BnzPackageRecordModel;
import com.rsd.mapper.BnzPackageRecordMapper;
import com.rsd.service.BnzPackageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @ClassName hdx
 * @Description 打包资质记录
 * @Date 2019/7/1 16:53
 * @Version 1.0
 **/
@Service("bnzPackageRecord")
public class BnzPackageRecordServiceImpl implements BnzPackageRecordService {
    @Autowired
    private BnzPackageRecordMapper bnzPackageRecordMapper;

    @Override
    public void insert(BnzPackageRecordModel bnzPackageRecordModel) throws Exception {
        bnzPackageRecordMapper.insert(bnzPackageRecordModel);
    }

    @Override
    public Page<List<BnzPackageRecordModel>> findPackageRecordPage(BnzPackageRecordModel bnzPackageRecordModel) throws Exception {
        Page<List<BnzPackageRecordModel>> page = PageHelper.startPage(bnzPackageRecordModel.getPageInput().getCurrent(), bnzPackageRecordModel.getPageInput().getSize());
        List<BnzPackageRecordModel> bnzPackageRecordModelList = bnzPackageRecordMapper.findPackageRecordPage(bnzPackageRecordModel);
        return page;
    }

    @Override
    public List<BnzPackageRecordModel> selectInstitutionQuaPackState(BnzPackageRecordModel bnzPackageRecordModel) throws Exception {
        List<BnzPackageRecordModel> bnzPackageRecordModelList = bnzPackageRecordMapper.selectInstitutionQuaPackState(bnzPackageRecordModel);
        return bnzPackageRecordModelList;
    }

    @Override
    public List<BnzPackageRecordModel> selectProductQuaQuaPackState(BnzPackageRecordModel bnzPackageRecordModel) throws Exception {
        List<BnzPackageRecordModel> bnzPackageRecordModelList = bnzPackageRecordMapper.selectProductQuaQuaPackState(bnzPackageRecordModel);
        return bnzPackageRecordModelList;
    }
}
