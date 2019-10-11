package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.BnzProductQuaModel;
import com.rsd.mapper.BnzProductQuaMapper;
import com.rsd.service.BnzProductQuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @ClassName hdx
 * @Description 产品资质服务类
 * @Date 2019/5/14 16:22
 * @Version 1.0
 **/
@Service("bnzProductQuaService")
public class BnzProductQuaServiceImpl implements BnzProductQuaService {
    @Autowired
    private BnzProductQuaMapper bnzProductQuaMapper;

    /**
     * @author hdx
     * @Description 查询企业所属的产品资质数量列表
     * @Date 2019/5/14 17:22
     * @Param [bnzProductQuaModel]
     * @Return com.github.pagehelper.Page<java.util.List < BnzProductQuaModel>>
     */
    public Page<List<BnzProductQuaModel>> searchBnzProductQuaList(BnzProductQuaModel bnzProductQuaModel) throws Exception {
        Page<List<BnzProductQuaModel>> page = PageHelper.startPage(bnzProductQuaModel.getPageInput().getCurrent(), bnzProductQuaModel.getPageInput().getSize());
        bnzProductQuaMapper.searchBnzProductQuaList(bnzProductQuaModel);
        return page;
    }

    /**
     * @author hdx
     * @Description 查询企业产品的资质列表详细信息
     * @Date 2019/5/14 17:22
     * @Param [bnzProductQuaModel]
     * @Return com.github.pagehelper.Page<java.util.List < BnzProductQuaModel>>
     */
    public Page<List<BnzProductQuaModel>> searchBnzProductQuaDetailList(BnzProductQuaModel bnzProductQuaModel) throws Exception {
        Page<List<BnzProductQuaModel>> page = PageHelper.startPage(bnzProductQuaModel.getPageInput().getCurrent(), bnzProductQuaModel.getPageInput().getSize());
        bnzProductQuaMapper.searchBnzProductQuaDetailList(bnzProductQuaModel);
        return page;
    }

    @Override
    public List<BnzProductQuaModel> searchPackRecrodProductList(BnzProductQuaModel bnzProductQuaModel) throws Exception {
        List<BnzProductQuaModel> list = bnzProductQuaMapper.searchBnzProductQuaDetailList(bnzProductQuaModel);
        return list;
    }

    @Override
    public void insertBnzProductQuaDetail(BnzProductQuaModel bnzProductQuaModel) throws Exception {
        bnzProductQuaMapper.insertModel(bnzProductQuaModel);
    }

    @Override
    public void deleteBnzProductQuaDetailById(BnzProductQuaModel bnzProductQuaModel) throws Exception {
        bnzProductQuaMapper.updateDelByRecordNotNull(bnzProductQuaModel);
    }

    @Override
    public void updateBnzProductQuaDetail(BnzProductQuaModel bnzProductQuaModel) throws Exception {
        bnzProductQuaMapper.updateModel(bnzProductQuaModel);
    }
}
