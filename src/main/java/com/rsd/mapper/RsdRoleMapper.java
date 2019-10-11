package com.rsd.mapper;

import com.rsd.domain.RsdRole;
import com.rsd.domain.RsdRoleModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RsdRoleMapper extends Mapper<RsdRole> {


    Long getId();

    List<RsdRoleModel> queryRoleList(@Param("model") RsdRoleModel model);
}