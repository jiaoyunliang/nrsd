package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzProjectInfoModel;

import java.util.List;

/**
 * @author hdx
 * @Description 项目管理服务类
 * @Date 16:13 2019/4/22
 */
public interface BnzProjectInfoService {
    Page<List<BnzProjectInfoModel>> searchProjectInfoList(BnzProjectInfoModel projectInfoModel) throws Exception;

    void addProjectInfo(BnzProjectInfoModel projectInfoModel) throws Exception;

    void updateProjectInfo(BnzProjectInfoModel projectInfoModel) throws Exception;

    void deleteProjectInfo(BnzProjectInfoModel projectInfoModel) throws Exception;
}
