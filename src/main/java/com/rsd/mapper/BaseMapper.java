package com.rsd.mapper;

import org.apache.ibatis.annotations.Param;

public interface BaseMapper<T> {

    T get(@Param("id") Long id) throws Exception;

    /**
     * 添加
     *
     * @param model 要添加的账号对象
     * @return 成功返回  1,失败返回 0
     */
    int insertModel(@Param("model") T model) throws Exception;

    /**
     * 更新
     *
     * @param model 要更新的对象
     * @return 操作影响的记录数（此方法按照id修改，因此成功返回 1 ）成功返回  1  失败返回0
     */
    int updateModel(@Param("model") T model) throws Exception;

    /**
     * 删除
     *
     * @param id 要删除的id
     * @return 操作影响的记录数, 成功返回  1,失败返回 0
     */
    int deleteModel(@Param("id") Long id) throws Exception;
}
