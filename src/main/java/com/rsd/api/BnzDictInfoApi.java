package com.rsd.api;

import com.rsd.domain.RsdDictInfo;
import com.rsd.service.BnzDictInfoService;
import com.rsd.utils.JsonOut;
import com.rsd.utils.MessageManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @data 2019-04-23
 * @modifyUser
 * @modifyDate
 */
@Api(value = "字典", description = "字典")
@RestController
@RequestMapping("/bnzDictInfoApi")
public class BnzDictInfoApi {

    private static final Logger logger = LoggerFactory.getLogger(BnzDictInfoApi.class);

    @Autowired
    private BnzDictInfoService bnzDictInfoService;

    @Autowired
    private MessageManager messageManager;

    @ApiOperation(value = "queryDictByCategoryId", notes = "queryDictByCategoryId")
    @PostMapping(value = "/queryDictByCategoryId")
    public HashMap queryDictByCategoryId(@RequestBody RsdDictInfo param){
        List<RsdDictInfo> list = bnzDictInfoService.queryDictByCategoryId(param);
        return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(list).build();
    }

}
