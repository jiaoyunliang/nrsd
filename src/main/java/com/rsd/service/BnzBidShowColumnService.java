package com.rsd.service;

import com.rsd.domain.BnzBidShowColumn;

import java.util.List;

/**
 * @author jiaoyl
 * @data 2019-06-27
 * @modifyUser
 * @modifyDate
 */
public interface BnzBidShowColumnService {
    List<BnzBidShowColumn> queryBidShowColumn(Long projectId)throws Exception;

    int updateBidShow(List<BnzBidShowColumn> bnzBidShowColumns)throws Exception;
}
