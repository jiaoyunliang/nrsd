package com.rsd.mapper;

import com.rsd.domain.BnzOrgDetail;
import com.rsd.domain.BnzOrgDetailModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzOrgDetailMapper extends Mapper<BnzOrgDetail> {

    List<BnzOrgDetailModel> queryOrgDetailList(@Param("model") BnzOrgDetailModel model);

    BnzOrgDetailModel queryOrgDetailById(@Param("model") BnzOrgDetailModel model);
}