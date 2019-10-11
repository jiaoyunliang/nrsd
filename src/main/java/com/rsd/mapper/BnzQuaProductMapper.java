package com.rsd.mapper;

import com.rsd.domain.BnzQuaProductModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BnzQuaProductMapper {

    public List<BnzQuaProductModel> getQuaTypeByProIdPage(@Param("model") BnzQuaProductModel quaPro);

    public int deleteQuaPro(BnzQuaProductModel quaPro);

    public int deleteQuaProByProIdAndQuaTypeId(BnzQuaProductModel quaPro);

    public int deleteQuaProByProIdAndInfoId(BnzQuaProductModel quaPro);

    public int insertQuaPro(BnzQuaProductModel quaPro);

    /**
     * 根据非空字段查询数据
     *
     * @param quaPro
     * @return
     */
    public int findQuaProByRecordNotNull(BnzQuaProductModel quaPro);
}
