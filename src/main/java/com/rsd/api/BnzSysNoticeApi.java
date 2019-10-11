package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.RsdAccountModel;
import com.rsd.domain.BnzSysNotice;
import com.rsd.domain.BnzSysNoticeModel;
import com.rsd.service.BnzSysNoticeService;
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
@Api(value = "系统通知", description = "系统通知")
@RestController
@RequestMapping("/bnzSysNoticeApi")
public class BnzSysNoticeApi {


    private static final Logger logger = LoggerFactory.getLogger(BnzSysNoticeApi.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private BnzSysNoticeService bnzSysNoticeService;


    @ApiOperation(value = "querySysNoticePage", notes = "querySysNoticePage")
    @PostMapping(value = "/querySysNoticePage")
    public HashMap querySysNoticePage(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzSysNoticeModel param) {

        try {
            Page<List> page = bnzSysNoticeService.querySysNoticePage(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("page").addData(page.getResult()).addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("querySysNoticePage", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }


    @ApiOperation(value = "saveNotice", notes = "saveNotice")
    @PostMapping(value = "/saveNotice")
    public HashMap saveNotice(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzSysNotice param) {
        logger.info("传入参数" + param);
        try {
            RsdAccountModel account = sessionManager.getCurrentUser();

            if (param.getId() == null) {
                param.setCreateUser(account.getUserName());
                bnzSysNoticeService.saveNotice(param);
            } else {
                param.setUpdateTime(new Date());
                param.setUpdateUser(account.getUserName());
                bnzSysNoticeService.updateNotice(param);
            }

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("saveNotice", e);
        }
        return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addException(messageManager.getMessage("ERROR.0006")).build();
    }

}
