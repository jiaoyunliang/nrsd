package com.rsd.service;

import com.rsd.domain.Example;

/**
 * @author tony
 * @data 2019-03-19
 * @modifyUser
 * @modifyDate
 */
public interface ExampleService {

    void insert(Example example);

    Example getExampleById(Long id);
}
