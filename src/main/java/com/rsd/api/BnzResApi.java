package com.rsd.api;

import com.rsd.domain.RsdRes;
import com.rsd.service.BnzResService;
import com.rsd.utils.Const;
import com.rsd.utils.JsonOut;
import com.rsd.utils.MessageManager;
import com.rsd.utils.ReqBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author tony
 * @data 2019-03-27
 * @modifyUser
 * @modifyDate
 */
@Api(value = "资源", description = "资源")
@RestController
@RequestMapping("/bnzResApi")
public class BnzResApi {


    private static final Logger logger = LoggerFactory.getLogger(BnzResApi.class);


    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BnzResService bnzResService;


    @ApiOperation(value = "queryResList", notes = "queryResList")
    @PostMapping(value = "/queryResList")
    public HashMap queryResList(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody ReqBean<RsdRes> param) {

        logger.info("传入参数"+param.getParam());
        try {
            List list = bnzResService.queryResList(param.getParam());

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0001")).addDataName("data").addData(list).build();
        } catch (Exception e) {
            logger.error("queryResList", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }

    @ApiOperation(value = "delResById", notes = "delResById")
    @PostMapping(value = "/delResById")
    public HashMap delResById(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody ReqBean<Long> param) {

        logger.info("传入参数"+param.getParam());
        try{
            bnzResService.deleteById(param.getParam());
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addMessage(messageManager.getMessage("ERROR.0007")).build();
    }

    @ApiOperation(value = "saveRes", notes = "saveRes")
    @PostMapping(value = "/saveRes")
    public HashMap saveRes(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody ReqBean<RsdRes> param) {
        logger.info("传入参数"+param.getParam());

        try {
            bnzResService.saveRes(param.getParam());

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("saveRes", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }

    @ApiOperation(value = "updateRes", notes = "updateRes")
    @PostMapping(value = "/updateRes")
    public HashMap updateRes(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody ReqBean<RsdRes> param) {
        logger.info("传入参数"+param.getParam());
        try {
            bnzResService.updateRes(param.getParam());

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("updateRes", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }
}
