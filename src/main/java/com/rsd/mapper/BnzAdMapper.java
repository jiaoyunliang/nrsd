package com.rsd.mapper;

import com.rsd.domain.BnzAd;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzAdMapper extends Mapper<BnzAd> {

    List<BnzAd> queryAdList(@Param("model") BnzAd model);
}