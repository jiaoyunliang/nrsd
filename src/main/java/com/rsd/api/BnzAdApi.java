package com.rsd.api;

import com.rsd.domain.BnzAd;
import com.rsd.service.BnzAdService;
import com.rsd.utils.Const;
import com.rsd.utils.JsonOut;
import com.rsd.utils.MessageManager;
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
 * @data 2019-05-07
 * @modifyUser
 * @modifyDate
 */
@Api(value = "AD", description = "AD")
@RestController
@RequestMapping("/bnzAdApi")
public class BnzAdApi {


    private static final Logger logger = LoggerFactory.getLogger(BnzAdApi.class);

    @Autowired
    private BnzAdService bnzAdService;


    @Autowired
    private MessageManager messageManager;


    @ApiOperation(value = "updateAd", notes = "updateAd")
    @PostMapping(value = "/updateAd")
    public HashMap updateAd(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzAd param) {
        logger.info("传入参数"+param);
        try {
            bnzAdService.updateAd(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("updateAd",e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ApiOperation(value = "queryAdList", notes = "queryAdList")
    @PostMapping(value = "/queryAdList")
    public HashMap queryAdList(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzAd param) {
        logger.info("传入参数"+param);
        try {
            List<BnzAd> list = bnzAdService.queryAdList(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(list).build();
        } catch (Exception e) {
            logger.error("queryAdList",e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }
}
