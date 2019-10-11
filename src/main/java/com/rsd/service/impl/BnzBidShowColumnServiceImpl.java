package com.rsd.service.impl;

import com.rsd.domain.BnzBidShowColumn;
import com.rsd.mapper.BnzBidShowColumnMapper;
import com.rsd.service.BnzBidShowColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author tony
 * @data 2019-05-07
 * @modifyUser
 * @modifyDate
 */
@Service("bnzBidShowColumnService")
public class BnzBidShowColumnServiceImpl implements BnzBidShowColumnService {

    @Autowired
    private BnzBidShowColumnMapper bnzBidShowColumnMapper;

    @Override
    public List<BnzBidShowColumn> queryBidShowColumn(Long projectId)throws Exception {
        BnzBidShowColumn bnzBidShowColumn = new BnzBidShowColumn();
        bnzBidShowColumn.setProjectId(projectId);
        Example example = new Example(BnzBidShowColumn.class);
        example.createCriteria().andEqualTo(bnzBidShowColumn);
        example.orderBy("columnOrder");
        return this.bnzBidShowColumnMapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBidShow(List<BnzBidShowColumn> bnzBidShowColumns) throws Exception {
        /**
         * 先删除掉原来的数据
         */
        if(!bnzBidShowColumns.isEmpty()) {
            BnzBidShowColumn bidShowColumnDel  = new BnzBidShowColumn();
            bidShowColumnDel.setProjectId(bnzBidShowColumns.get(0).getProjectId());
            this.bnzBidShowColumnMapper.delete(bidShowColumnDel);
        }

        for (BnzBidShowColumn bidShowColumn : bnzBidShowColumns) {
            this.bnzBidShowColumnMapper.insert(bidShowColumn);
        }

        return 0;
    }
}
