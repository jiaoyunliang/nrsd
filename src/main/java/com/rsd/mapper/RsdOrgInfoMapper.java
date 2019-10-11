package com.rsd.mapper;

import com.rsd.domain.RsdOrgInfo;
import com.rsd.domain.RsdOrgInfoModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RsdOrgInfoMapper extends Mapper<RsdOrgInfo>, BaseMapper<RsdOrgInfo> {

    List<RsdOrgInfo> queryOrgList(@Param("model") RsdOrgInfo model);

    List<RsdOrgInfoModel> queryOrgPage(@Param("model") RsdOrgInfoModel model);

    List<RsdOrgInfoModel> queryEnterpriseList(@Param("model") RsdOrgInfoModel model);

    Long getId();

}