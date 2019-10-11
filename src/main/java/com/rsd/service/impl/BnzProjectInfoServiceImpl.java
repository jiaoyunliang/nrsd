package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.BnzBidShowColumn;
import com.rsd.domain.BnzProjectInfoModel;
import com.rsd.mapper.BnzProjectInfoMapper;
import com.rsd.service.BnzBidShowColumnService;
import com.rsd.service.BnzProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @ClassName hdx
 * @Description 项目管理服务类
 * @Date 2019/4/22 16:21
 * @Version 1.0
 **/
@Service("bnzProjectInfoService")
public class BnzProjectInfoServiceImpl implements BnzProjectInfoService {

    @Autowired
    private BnzProjectInfoMapper bnzProjectInfoMapper;

    @Autowired
    private BnzBidShowColumnService bnzBidShowColumnService;

    /**
     * @author hdx
     * @Description 根据项目类型查询项目列表
     * @Date 2019/4/22 16:30
     * @Param [projectInfo]
     * @Return java.util.List<BnzProjectInfo>
     */
    @Override
    public Page<List<BnzProjectInfoModel>> searchProjectInfoList(BnzProjectInfoModel projectInfoModel) throws Exception {
        Page<List<BnzProjectInfoModel>> page = PageHelper.startPage(projectInfoModel.getPageInput().getCurrent(), projectInfoModel.getPageInput().getSize());
        List<BnzProjectInfoModel> projectInfoList = bnzProjectInfoMapper.searchProjectInfoList(projectInfoModel);
        return page;
    }

    /**
     * @author hdx
     * @Description 添加项目信息
     * @Date 2019/4/23 9:53
     * @Param [bnzProjectInfo]
     * @Return void
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addProjectInfo(BnzProjectInfoModel projectInfoModel) throws Exception {
        bnzProjectInfoMapper.addProjectInfo(projectInfoModel);

        for(BnzBidShowColumn bidShowColumn :projectInfoModel.getBidShowColumnList()){
            bidShowColumn.setProjectId(projectInfoModel.getId());
            bidShowColumn.setCreateTime(new Date());
            bidShowColumn.setCreateUser(projectInfoModel.getCreateUser());
            bidShowColumn.setIsDel(0);
        }
        bnzBidShowColumnService.updateBidShow(projectInfoModel.getBidShowColumnList());
    }

    /**
     * @author hdx
     * @Description 添加项目信息
     * @Date 2019/4/23 9:53
     * @Param [bnzProjectInfo]
     * @Return void
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateProjectInfo(BnzProjectInfoModel projectInfoModel) throws Exception {

        bnzProjectInfoMapper.updateByPrimaryKey(projectInfoModel);

        for(BnzBidShowColumn bidShowColumn :projectInfoModel.getBidShowColumnList()){
            bidShowColumn.setProjectId(projectInfoModel.getId());
            bidShowColumn.setCreateTime(new Date());
            bidShowColumn.setCreateUser(projectInfoModel.getCreateUser());
            bidShowColumn.setIsDel(0);
        }
        bnzBidShowColumnService.updateBidShow(projectInfoModel.getBidShowColumnList());
    }

    /**
     * @author hdx
     * @Description 删除项目
     * @Date 2019/4/23 10:06
     * @Param [bnzProjectInfo]
     * @Return void
     */
    @Override
    public void deleteProjectInfo(BnzProjectInfoModel projectInfoModel) throws Exception {
        bnzProjectInfoMapper.deleteProjectInfo(projectInfoModel);
    }
}
