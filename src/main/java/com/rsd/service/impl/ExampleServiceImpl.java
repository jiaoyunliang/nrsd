package com.rsd.service.impl;

import com.rsd.domain.Example;
import com.rsd.mapper.ExampleMapper;
import com.rsd.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tony
 * @data 2019-03-19
 * @modifyUser
 * @modifyDate
 */
@Service("exampleService")
public class ExampleServiceImpl implements ExampleService {

    private static final Logger logger = LoggerFactory.getLogger(ExampleServiceImpl.class);

    @Autowired
    private ExampleMapper exampleMapper;

    @Override
    public void insert(Example example) {
        example.setId(exampleMapper.getId());
        exampleMapper.insertSelective(example);
//        exampleMapper.insert(example);
    }

    @Override
    public Example getExampleById(Long id) {
        return exampleMapper.selectByPrimaryKey(id);
    }
}
