package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzInstitutionQuaModel;

import java.util.List;

public interface BnzInstitutionQuaService {
    public Page<List<BnzInstitutionQuaModel>> searchBnzInstitutionQuaList(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;

    public Page<List<BnzInstitutionQuaModel>> searchBnzInstitutionQuaDetailList(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;

    public Page<List<BnzInstitutionQuaModel>> searchBnzInstitutionList(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;

    public List<BnzInstitutionQuaModel> searchPackRecrodInstitutionList(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;

    public void insertBnzInstitutionDetail(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;

    public void deleteBnzInstitutionDetailById(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;

    public void updateBnzInstitutionDetail(BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;
}
