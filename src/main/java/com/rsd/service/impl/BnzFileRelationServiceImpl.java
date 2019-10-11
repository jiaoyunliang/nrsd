package com.rsd.service.impl;

import com.rsd.domain.BnzFileRelation;
import com.rsd.mapper.BnzFileRelationMapper;
import com.rsd.service.BnzFileRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @ClassName hdx
 * @Description 附件处理服务类
 * @Date 2019/5/7 16:01
 * @Version 1.0
 **/
@Service("bnzFileRelationService")
public class BnzFileRelationServiceImpl implements BnzFileRelationService {
    @Autowired
    private BnzFileRelationMapper bnzFileRelationMapper;

    /*
     * @author hdx
     * @Description  附件删除
     * @Date 2019/5/7 16:02
     * @Param [categoryId, category]
     * @Return void
     */
    public void deleteFileByCategoryId(Long categoryId, Integer category) throws Exception {
        bnzFileRelationMapper.deleteFileByCategoryId(categoryId, category);
    }

    /**
     * @author hdx
     * @Description 根据附件类别查询附件信息
     * @Date 2019/5/7 16:14
     * @Param [category, categoryId]
     * @Return java.util.List<BnzFileRelation>
     */
    public List<BnzFileRelation> searchFileByCategoryId(Integer category, Long categoryId) throws Exception {
        return bnzFileRelationMapper.searchFileByCategoryId(category, categoryId);
    }

    /**
     * @author hdx
     * @Description 根据附件编号查询附件信息
     * @Date 2019/5/7 16:14
     * @Param [id]
     * @Return BnzFileRelation
     */
    public BnzFileRelation get(Long id) throws Exception {
        return bnzFileRelationMapper.get(id);
    }

    /**
     * @author hdx
     * @Description 保存附件信息
     * @Date 2019/5/7 16:13
     * @Param [model]
     * @Return int
     */
    public int save(BnzFileRelation model) throws Exception {
        return bnzFileRelationMapper.insertModel(model);
    }

    /**
     * @author hdx
     * @Description 删除附件信息
     * @Date 2019/5/7 16:13
     * @Param [model]
     * @Return int
     */
    public int delete(BnzFileRelation model) throws Exception {
        return bnzFileRelationMapper.deleteModel(model.getId());
    }
}
