package com.rsd.service;

import com.rsd.domain.BnzFileRelation;

import java.util.List;

public interface BnzFileRelationService {
    void deleteFileByCategoryId(Long categoryId,
                                Integer category) throws Exception;

    List<BnzFileRelation> searchFileByCategoryId(
            Integer category,
            Long categoryId) throws Exception;

    BnzFileRelation get(Long id) throws Exception;

    int save(BnzFileRelation model) throws Exception;

    int delete(BnzFileRelation model) throws Exception;
}
