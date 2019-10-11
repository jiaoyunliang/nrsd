package com.rsd.mapper;

import com.rsd.domain.BnzInstitutionQua;
import com.rsd.domain.BnzInstitutionQuaModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzInstitutionQuaMapper extends Mapper<BnzInstitutionQua>, BaseMapper<BnzInstitutionQua> {

    public List<BnzInstitutionQuaModel> searchBnzInstitutionQuaList(@Param("model") BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;

    public List<BnzInstitutionQuaModel> searchBnzInstitutionQuaDetailList(@Param("model") BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;

    public List<BnzInstitutionQuaModel> searchBnzInstitutionList(@Param("model") BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;

    public List<BnzInstitutionQuaModel> searchPackRecrodInstitutionList(@Param("model") BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;

    public void deleteInstitutionQua(@Param("model") BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;

    public void updateInstitutionQua(@Param("model") BnzInstitutionQuaModel bnzInstitutionQuaModel) throws Exception;

    public List<BnzInstitutionQuaModel> searchBnzInstitutionQuaExprieList();
}