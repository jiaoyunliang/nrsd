package com.rsd.service.impl;

import com.rsd.domain.BnzQuaExtItem;
import com.rsd.mapper.BnzQuaExtItemMapper;
import com.rsd.service.BnzQuaExtItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @ClassName hdx
 * @Description 资质扩展属性
 * @Date 2019/5/8 17:08
 * @Version 1.0
 **/
@Service("bnzQuaExtItemService")
public class BnzQuaExtItemServiceImpl implements BnzQuaExtItemService {

    @Autowired
    private BnzQuaExtItemMapper bnzQuaExtItemMapper;

    @Override
    public List<BnzQuaExtItem> findQuaExtItemListByRecordNotNull(BnzQuaExtItem model) {
        return bnzQuaExtItemMapper.findQuaExtItemListByRecordNotNull(model);
    }

    @Override
    public List<BnzQuaExtItem> findQuaExtItemListByQuaCode(BnzQuaExtItem model) {
        return bnzQuaExtItemMapper.findQuaExtItemListByQuaCode(model);
    }

    @Override
    public List<BnzQuaExtItem> findQuaExtItemListByQuaType(BnzQuaExtItem model) {
        return bnzQuaExtItemMapper.findQuaExtItemListByQuaType(model);
    }
}
