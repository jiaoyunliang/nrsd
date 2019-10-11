package com.rsd.mapper;

import com.rsd.domain.BnzPackageRecordModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BnzPackageRecordMapper {

    void insert(BnzPackageRecordModel packageRecord);

    List<BnzPackageRecordModel> findPackageRecordPage(@Param("model") BnzPackageRecordModel packageRecord);

    List<BnzPackageRecordModel> selectInstitutionQuaPackState(@Param("model") BnzPackageRecordModel packageRecordModel);

    List<BnzPackageRecordModel> selectProductQuaQuaPackState(@Param("model") BnzPackageRecordModel packageRecordModel);
}
