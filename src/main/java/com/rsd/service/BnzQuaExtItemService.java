package com.rsd.service;

import com.rsd.domain.BnzQuaExtItem;

import java.util.List;

public interface BnzQuaExtItemService {

    List<BnzQuaExtItem> findQuaExtItemListByRecordNotNull(BnzQuaExtItem model);

    List<BnzQuaExtItem> findQuaExtItemListByQuaCode(BnzQuaExtItem model);

    List<BnzQuaExtItem> findQuaExtItemListByQuaType(BnzQuaExtItem model);
}
