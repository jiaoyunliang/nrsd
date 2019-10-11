package com.rsd.mapper;

import com.rsd.domain.BnzBidDetail;
import com.rsd.domain.BnzBidDetailModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzBidDetailMapper extends Mapper<BnzBidDetail> {
    int insertList(List<BnzBidDetail> list)throws Exception;

    int deleteBidDetail(BnzBidDetail bnzBidDetail)throws Exception;

    List<BnzBidDetail> queryList(@Param("model") BnzBidDetailModel model)throws Exception;
}