package com.rsd.api;

import com.rsd.domain.Example;
import com.rsd.service.ExampleService;
import com.rsd.utils.ReqBean;
import com.rsd.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author tony
 * @data 2019-03-19
 * @modifyUser
 * @modifyDate
 */
@Api(value = "测试", description = "测试")
@RestController
@RequestMapping("/testApi")
public class TestApi {

    private static final Logger logger = LoggerFactory.getLogger(TestApi.class);

    @Autowired
    private ExampleService exampleService;

    @ApiOperation(value = "saveExample", notes = "save")
    @PostMapping(value = "/saveExample")
    public RespBean saveExample(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody ReqBean<String> param, HttpServletRequest request) {
        RespBean respBean;

        Example ex = new Example();
        ex.setCreateUser(param.getParam());
        ex.setCreateTime(new Date());
        exampleService.insert(ex);

        if(StringUtils.isEmpty(param.getParam())){
            respBean = RespBean.error("param is not null");
        } else {
            respBean = RespBean.ok("input:"+param.getParam(),ex);
        }

        logger.info("传入参数"+param.getParam());

        return respBean;
    }


    @ApiOperation(value = "getExampleById", notes = "get by id")
    @PostMapping(value = "/getExampleById")
    public RespBean getExample(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody ReqBean<Long> param, HttpServletRequest request) {
        RespBean respBean;

        Example ex = exampleService.getExampleById(param.getParam());

        if(null==ex){
            respBean = RespBean.error("null");
        } else {
            respBean = RespBean.ok("input:"+param.getParam(),ex);
        }

        logger.info("传入参数"+param.getParam());

        return respBean;
    }
}
