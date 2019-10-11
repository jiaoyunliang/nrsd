package com.rsd.mapper;

import com.rsd.domain.RsdDictInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzDictInfoMapper extends Mapper<RsdDictInfo>,BaseMapper<RsdDictInfo>{

    List<RsdDictInfo> queryDictByCategoryId(@Param("model") RsdDictInfo model);

    RsdDictInfo getDetailDictInfo(@Param("model") RsdDictInfo model);
}