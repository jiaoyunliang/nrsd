package com.rsd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsd.domain.BnzBidDetail;
import com.rsd.domain.BnzBidDetailModel;
import com.rsd.mapper.BnzBidDetailMapper;
import com.rsd.service.BnzBidDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("BnzBidDetailService")
public class BnzBidDetailServiceImpl implements BnzBidDetailService {

    @Autowired
    private BnzBidDetailMapper bnzBidDetailMapper;

    @Override
    public int deleteBidDetail(Long projectId) throws Exception {
        BnzBidDetail bnzBidDetail = new BnzBidDetail();
        bnzBidDetail.setIsDel(1);
        bnzBidDetail.setProjectId(projectId);
        this.bnzBidDetailMapper.deleteBidDetail(bnzBidDetail);

        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveBidDetail(List<BnzBidDetail> list) throws Exception {

        this.bnzBidDetailMapper.insertList(list);
        return 0;
    }

    @Override
    public Page<List> queryBidDetail(BnzBidDetailModel model) throws Exception {
        Page<List> page = PageHelper.startPage(model.getPageInput().getCurrent(), model.getPageInput().getSize());
         this.bnzBidDetailMapper.queryList(model);
        return page;
    }
}
