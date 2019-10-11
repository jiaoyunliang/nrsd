package com.rsd.service;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzPackageRecordModel;

import java.util.List;

public interface BnzPackageRecordService {

    void insert(BnzPackageRecordModel bnzPackageRecordModel) throws Exception;

    Page<List<BnzPackageRecordModel>> findPackageRecordPage(BnzPackageRecordModel bnzPackageRecordModel) throws Exception;

    List<BnzPackageRecordModel> selectInstitutionQuaPackState(BnzPackageRecordModel bnzPackageRecordModel) throws Exception;

    List<BnzPackageRecordModel> selectProductQuaQuaPackState(BnzPackageRecordModel bnzPackageRecordModel) throws Exception;
}
