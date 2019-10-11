package com.rsd.mapper;

import com.rsd.domain.BnzProduct;
import com.rsd.domain.BnzProductModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzProductMapper extends Mapper<BnzProduct> {


    Long getId();

    /**
     * 管理端
     * @param model
     * @return
     */
    List<BnzProductModel> queryProductList(@Param("model") BnzProductModel model);

    /**
     * 医院端
     * @param model
     * @return
     */
    List<BnzProductModel> selectProductList(@Param("model") BnzProductModel model);



    BnzProductModel queryProductById(@Param("id") Long id);

}