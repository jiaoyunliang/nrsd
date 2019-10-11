package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.RsdAccountModel;
import com.rsd.domain.BnzFeedback;
import com.rsd.domain.BnzFeedbackModel;
import com.rsd.service.BnzFeedbackService;
import com.rsd.utils.*;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author tony
 * @data 2019-05-06
 * @modifyUser
 * @modifyDate
 */
@Api(value = "信息反馈", description = "信息反馈")
@RestController
@RequestMapping("/bnzFeedbackApi")
public class BnzFeedbackApi {

    private static final Logger logger = LoggerFactory.getLogger(BnzFeedbackApi.class);

    @Autowired
    private BnzFeedbackService bnzFeedbackService;

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @ApiOperation(value = "queryFeedbackPage", notes = "queryFeedbackPage")
    @PostMapping(value = "/queryFeedbackPage")
    public HashMap queryFeedbackPage(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzFeedbackModel param) {

        try {
            Page<List> page = bnzFeedbackService.queryFeedbackPage(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("page").addData(page.getResult()).addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("queryFeedbackPage", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }

    @ApiOperation(value = "updateFeedback", notes = "updateFeedback")
    @PostMapping(value = "/updateFeedback")
    public HashMap updateFeedback(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzFeedback param) {
        logger.info("传入参数" + param);
        try {
            RsdAccountModel account = sessionManager.getCurrentUser();
            param.setUpdateTime(new Date());
            param.setUpdateUser(account.getUserName());
            bnzFeedbackService.updateFeedback(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("updateFeedback", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }
}
