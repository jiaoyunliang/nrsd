package com.rsd.mapper;

import com.rsd.domain.BnzFileRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BnzFileRelationMapper extends BaseMapper<BnzFileRelation> {
    void deleteFileByCategoryId(@Param("categoryId") Long categoryId,
                                @Param("category") Integer category) throws Exception;

    List<BnzFileRelation> searchFileByCategoryId(
            @Param("category") Integer category,
            @Param("categoryId") Long categoryId) throws Exception;
}