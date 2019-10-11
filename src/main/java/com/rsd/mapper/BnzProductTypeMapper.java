package com.rsd.mapper;

import com.rsd.domain.BnzProductType;
import com.rsd.domain.BnzProductTypeModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzProductTypeMapper extends Mapper<BnzProductType> {

    Long getId();

    List<BnzProductTypeModel> queryProductType();

    List<BnzProductType> queryOrgTypes(@Param("orgId") Long orgId);

    int updateProductTypeClickNum(@Param("id") Long id);

}