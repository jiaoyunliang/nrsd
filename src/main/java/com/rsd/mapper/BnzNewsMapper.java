package com.rsd.mapper;

import com.rsd.domain.BnzNews;
import com.rsd.domain.BnzNewsModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BnzNewsMapper extends Mapper<BnzNews> {

    Long getId();

    List queryNewsList(@Param("model") BnzNewsModel model);
}