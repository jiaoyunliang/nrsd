package com.rsd.mapper;

import com.rsd.domain.BnzProjectInfo;
import com.rsd.domain.BnzProjectInfoModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzProjectInfoMapper extends Mapper<BnzProjectInfo> {

    List<BnzProjectInfoModel> searchProjectInfoList(@Param("projectInfo") BnzProjectInfoModel bnzProjectInfoModel) throws Exception;

    void addProjectInfo(@Param("projectInfo") BnzProjectInfoModel bnzProjectInfoModel) throws Exception;

    void deleteProjectInfo(@Param("projectInfo") BnzProjectInfoModel bnzProjectInfoModel) throws Exception;
}