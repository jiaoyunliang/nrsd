package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzProductQuaModel;

import java.util.List;

public interface BnzProductQuaService {

    public Page<List<BnzProductQuaModel>> searchBnzProductQuaList(BnzProductQuaModel bnzProductQuaModel) throws Exception;

    public Page<List<BnzProductQuaModel>> searchBnzProductQuaDetailList(BnzProductQuaModel bnzProductQuaModel) throws Exception;

    public List<BnzProductQuaModel> searchPackRecrodProductList(BnzProductQuaModel bnzProductQuaModel) throws Exception;

    public void insertBnzProductQuaDetail(BnzProductQuaModel bnzProductQuaModel) throws Exception;

    public void deleteBnzProductQuaDetailById(BnzProductQuaModel bnzProductQuaModel) throws Exception;

    public void updateBnzProductQuaDetail(BnzProductQuaModel bnzProductQuaModel) throws Exception;

}
