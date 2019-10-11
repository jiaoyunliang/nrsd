package com.rsd.mapper;

import com.rsd.domain.BnzQuaExtItem;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzQuaExtItemMapper extends Mapper<BnzQuaExtItem> {

    List<BnzQuaExtItem> findQuaExtItemListByRecordNotNull(@Param("model") BnzQuaExtItem model);

    List<BnzQuaExtItem> findQuaExtItemListByQuaCode(@Param("model") BnzQuaExtItem model);


    List<BnzQuaExtItem> findQuaExtItemListByQuaType(@Param("model") BnzQuaExtItem model);
}