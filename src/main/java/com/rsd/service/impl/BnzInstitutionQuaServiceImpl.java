package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.BnzFileRelation;
import com.rsd.domain.BnzInstitutionQuaModel;
import com.rsd.mapper.BnzFileRelationMapper;
import com.rsd.mapper.BnzInstitutionQuaMapper;
import com.rsd.service.BnzInstitutionQuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author admin
 * @ClassName hdx
 * @Description 企业资质服务类
 * @Date 2019/5/7 15:32
 * @Version 1.0
 **/
@Service("bnzInstitutionQua")
public class BnzInstitutionQuaServiceImpl implements BnzInstitutionQuaService {
    @Autowired
    private BnzInstitutionQuaMapper bnzInstitutionQuaMapper;
    @Autowired
    private BnzFileRelationMapper bnzFileRelationMapper;

    /**
     * @author hdx
     * @Description 查询企业所拥有的资质列表以及所拥有的企业资质的数量
     * @Date 2019/5/14 17:17
     * @Param [bnzInstitutionQuaModel]
     * @Return com.github.pagehelper.Page<java.util.List < BnzInstitutionQuaModel>>
     */
    public Page<List<BnzInstitutionQuaModel>> searchBnzInstitutionQuaList(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception {
        Page<List<BnzInstitutionQuaModel>> page = PageHelper.startPage(bnzInstitutionQuaModel.getPageInput().getCurrent(), bnzInstitutionQuaModel.getPageInput().getSize());
        bnzInstitutionQuaMapper.searchBnzInstitutionQuaList(bnzInstitutionQuaModel);
        return page;
    }

    /**
     * @author hdx
     * @Description 查询企业资质的详细信息
     * @Date 2019/5/14 17:18
     * @Param [bnzInstitutionQuaModel]
     * @Return com.github.pagehelper.Page<java.util.List < BnzInstitutionQuaModel>>
     */
    public Page<List<BnzInstitutionQuaModel>> searchBnzInstitutionQuaDetailList(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception {
        Page<List<BnzInstitutionQuaModel>> page = PageHelper.startPage(bnzInstitutionQuaModel.getPageInput().getCurrent(), bnzInstitutionQuaModel.getPageInput().getSize());
        bnzInstitutionQuaMapper.searchBnzInstitutionQuaDetailList(bnzInstitutionQuaModel);
        return page;
    }

    /**
     * @author hdx
     * @Description 查询企业列表
     * @Date 2019/5/14 17:19
     * @Param [bnzInstitutionQuaModel]
     * @Return com.github.pagehelper.Page<java.util.List < BnzInstitutionQuaModel>>
     */
    public Page<List<BnzInstitutionQuaModel>> searchBnzInstitutionList(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception {
        Page<List<BnzInstitutionQuaModel>> page = PageHelper.startPage(bnzInstitutionQuaModel.getPageInput().getCurrent(), bnzInstitutionQuaModel.getPageInput().getSize());
        bnzInstitutionQuaMapper.searchBnzInstitutionList(bnzInstitutionQuaModel);
        return page;
    }

    @Override
    public List<BnzInstitutionQuaModel> searchPackRecrodInstitutionList(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception {
        List<BnzInstitutionQuaModel> list = bnzInstitutionQuaMapper.searchPackRecrodInstitutionList(bnzInstitutionQuaModel);
        return list;
    }

    /**
     * @author hdx
     * @Description 添加企业资质信息数据
     * @Date 2019/6/18 10:46
     * @Param [bnzInstitutionQuaModel]
     * @Return void
     */
    @Transactional
    public void insertBnzInstitutionDetail(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception {
        bnzInstitutionQuaMapper.insertModel(bnzInstitutionQuaModel);
        List<BnzFileRelation> list = bnzInstitutionQuaModel.getBnzFileRelation();
        Long id = bnzInstitutionQuaModel.getId();
        //先删除对应的附件业务数据
        if (list.size() > 0) {
            for (BnzFileRelation bnzFileRelation : list
            ) {
                bnzFileRelation.setCategoryId(id);
                bnzFileRelation.setCategory(new Long(1));//设置附件类型为 1.企业资质
                bnzFileRelation.setType(new Long(1));//设置文件类型为 1.图片
                bnzFileRelation.setCategoryId(bnzInstitutionQuaModel.getId());//设置附件类型对应的业务数据ID
                bnzFileRelation.setIsDel(new Long(0));//设置删除状态为 0.否
                bnzFileRelation.setCreateUser(bnzInstitutionQuaModel.getCreateUser());//设置新增数据用户
                bnzFileRelationMapper.insertModel(bnzFileRelation);
            }
        }
    }

    @Transactional
    public void deleteBnzInstitutionDetailById(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception {
        bnzFileRelationMapper.deleteFileByCategoryId(bnzInstitutionQuaModel.getId(), 1);
        bnzInstitutionQuaMapper.deleteInstitutionQua(bnzInstitutionQuaModel);
    }

    /**
     * @author hdx
     * @Description 更新企业资质信息数据
     * @Date 2019/6/18 10:48
     * @Param [bnzInstitutionQuaModel]
     * @Return void
     */
    @Transactional
    public void updateBnzInstitutionDetail(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception {
        bnzInstitutionQuaMapper.updateInstitutionQua(bnzInstitutionQuaModel);
        List<BnzFileRelation> list = bnzInstitutionQuaModel.getBnzFileRelation();
        Long id = bnzInstitutionQuaModel.getId();
        //先删除对应的附件业务数据
        bnzFileRelationMapper.deleteFileByCategoryId(id, 1);
        if (list.size() > 0) {
            for (BnzFileRelation bnzFileRelation : list
            ) {
                bnzFileRelation.setCategory(new Long(1));//设置附件类型为 1.企业资质
                bnzFileRelation.setType(new Long(1));//设置文件类型为 1.图片
                bnzFileRelation.setCategoryId(bnzInstitutionQuaModel.getId());//设置附件类型对应的业务数据ID
                bnzFileRelation.setIsDel(new Long(0));//设置删除状态为 0.否
                bnzFileRelation.setCreateUser(bnzInstitutionQuaModel.getUpdateUser());//设置新增数据用户
                bnzFileRelationMapper.insertModel(bnzFileRelation);
            }
        }
    }
}
