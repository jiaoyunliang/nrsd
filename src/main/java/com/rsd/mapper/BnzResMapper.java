package com.rsd.mapper;

import com.rsd.domain.RsdRes;
import com.rsd.domain.RsdRoleModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzResMapper extends Mapper<RsdRes> {

    List<RsdRes> queryResList(@Param("model") RsdRes res);

    List<RsdRes> queryResListByRoleId(@Param("model") RsdRoleModel model);

    Long getId();
}