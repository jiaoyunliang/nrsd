package com.rsd.mapper;

import com.rsd.domain.BnzProductQua;
import com.rsd.domain.BnzProductQuaModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzProductQuaMapper extends Mapper<BnzProductQua>, BaseMapper<BnzProductQuaModel> {
    /**
     * 企业资质列表 获取资质列表
     *
     * @param model 当传入orgid时  只取此orgid的数据数据，
     *              当传入quaCode只取此quaCode对应资质列表
     *              传入quaType  1.企业资质 2.产品资质
     * @return 资质分组列表
     */
    List<BnzProductQuaModel> getGroupQuaInfoByPage(@Param("model") BnzProductQuaModel model);

    /**
     * 根据资质编码 更新资质  资质编码必填
     *
     * @param model
     * @return
     */
    Integer updateByCodeAndType(@Param("model") BnzProductQuaModel model);

    /**
     * getGroupQuaInfoByPage
     * 按产品id查资质
     *
     * @param model
     * @param
     * @return
     */
    public List<BnzProductQuaModel> getQuaInfoByProIdPage(@Param("model") BnzProductQuaModel model);

    public Integer updateDelByRecordNotNull(BnzProductQuaModel model);

    public List<BnzProductQuaModel> findQuaInfoByRecordNotNull(BnzProductQuaModel model);

    public List<BnzProductQuaModel> getQuaListByPage(@Param("model") BnzProductQuaModel model);

    BnzProductQuaModel getQuaById(@Param("model") BnzProductQuaModel model);

    List<BnzProductQuaModel> findQuaInfoExport(@Param("model") BnzProductQuaModel model);

    List<BnzProductQuaModel> searchBnzProductQuaList(@Param("model") BnzProductQuaModel model);

    List<BnzProductQuaModel> searchBnzProductQuaDetailList(@Param("model") BnzProductQuaModel model);

    List<BnzProductQuaModel> searchBnzProductQuaExprieList();
}