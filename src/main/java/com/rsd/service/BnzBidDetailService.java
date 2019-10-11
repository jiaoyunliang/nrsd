package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzBidDetail;
import com.rsd.domain.BnzBidDetailModel;

import java.util.List;

/**
 * @author jiaoyl
 * @data 2019-06-27
 * @modifyUser
 * @modifyDate
 */
public interface BnzBidDetailService {
    int deleteBidDetail(Long projectId)throws Exception;

    int saveBidDetail(List<BnzBidDetail> list)throws Exception;

    Page<List> queryBidDetail(BnzBidDetailModel model)throws Exception;
}
