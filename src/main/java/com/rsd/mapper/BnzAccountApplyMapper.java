package com.rsd.mapper;

import com.rsd.domain.BnzAccountApply;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzAccountApplyMapper extends Mapper<BnzAccountApply> {


    Long getId();

    List<BnzAccountApply> queryAccountApply(@Param("model") BnzAccountApply model);
}