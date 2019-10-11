package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzAccountApply;
import com.rsd.domain.BnzAccountApplyModel;
import com.rsd.domain.RsdAccountModel;
import com.rsd.service.BnzAccountApplyService;
import com.rsd.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @data 2019-04-23
 * @modifyUser
 * @modifyDate
 */
@Api(value = "企业申请", description = "企业申请")
@RestController
@RequestMapping("/bnzAccountApplyApi")
public class BnzAccountApplyApi {

    private static final Logger logger = LoggerFactory.getLogger(BnzAccountApplyApi.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private BnzAccountApplyService bnzAccountApplyService;

    public HashMap saveAccountApply(@RequestBody BnzAccountApply param){
        
        return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
    }

    @ApiOperation(value = "updateAccountApply", notes = "updateAccountApply")
    @PostMapping(value = "/updateAccountApply")
    public HashMap updateAccountApply(@RequestBody BnzAccountApply param){
        try {
            Map<String,Object> map = new HashMap<>();
            RsdAccountModel account = sessionManager.getCurrentUser();
            param.setUpdateTime(new Date());
            param.setUpdateUser(account.getUserName());
            param.setIsRead(1);
            bnzAccountApplyService.updateAccountApply(param);
            //申请企业 未读数
            BnzAccountApply accountApply = new BnzAccountApply();
            accountApply.setIsRead(0);
            int applyNonReadNum = bnzAccountApplyService.queryAccountApplyNonReadNum(accountApply);

            map.put("applyNonReadNum",applyNonReadNum);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(map).build();
        } catch (Exception e){
            logger.error("updateAccountApply",e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }


    @ApiOperation(value = "queryAccountApply", notes = "queryAccountApply")
    @PostMapping(value = "/queryAccountApply")
    public HashMap queryAccountApply(@RequestBody BnzAccountApplyModel param){
        try {
            Page<List> page =bnzAccountApplyService.queryAccountApply(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page").addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e){
            logger.error("queryAccountApply",e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }

}
