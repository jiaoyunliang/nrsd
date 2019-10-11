package com.rsd.mapper;

import com.rsd.domain.BnzCollections;
import com.rsd.domain.BnzCollectionsModel;
import com.rsd.domain.BnzOrgDetailModel;
import com.rsd.domain.BnzProductModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzCollectionsMapper extends Mapper<BnzCollections> {

    Long getId();

    /**
     * 医院 关注的企业
     * @param model
     * @return
     */
    List<BnzOrgDetailModel> queryCollectionOrgPage(@Param("model") BnzCollectionsModel model);

    /**
     * 医院 关注和使用的产品
     * @param model
     * @return
     */
    List<BnzProductModel> queryCollectionProductPage(@Param("model") BnzCollectionsModel model);
}