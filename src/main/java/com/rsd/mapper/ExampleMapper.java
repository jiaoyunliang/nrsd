package com.rsd.mapper;

import com.rsd.domain.Example;
import tk.mybatis.mapper.common.Mapper;

public interface ExampleMapper extends Mapper<Example> {

    Long getId();
}