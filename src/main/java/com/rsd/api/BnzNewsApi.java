package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.RsdAccountModel;
import com.rsd.domain.BnzNews;
import com.rsd.domain.BnzNewsModel;
import com.rsd.service.BnzNewsService;
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
 * @data 2019-04-29
 * @modifyUser
 * @modifyDate
 */
@Api(value = "BnzNewsApi", description = "BnzNewsApi")
@RestController
@RequestMapping("/bnzNewsApi")
public class BnzNewsApi {

    private static final Logger logger = LoggerFactory.getLogger(BnzNewsApi.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private BnzNewsService bnzNewsService;


    @ApiOperation(value = "saveNews", notes = "saveNews")
    @PostMapping(value = "/saveNews")
    public HashMap saveNews(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzNews param) {
        logger.info("传入参数" + param);
        try {
            RsdAccountModel account = sessionManager.getCurrentUser();

            if (param.getId() == null) {
                param.setUpdateUser(account.getUserName());
                param.setUpdateTime(new Date());
                param.setCreateUser(account.getUserName());
                bnzNewsService.saveNews(param);
            } else {
                param.setUpdateUser(account.getUserName());
                param.setUpdateTime(new Date());
                bnzNewsService.updateNews(param);

            }
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("saveNews", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ApiOperation(value = "queryNewsPage", notes = "queryNewsPage")
    @PostMapping(value = "/queryNewsPage")
    public HashMap queryNewsPage(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzNewsModel param) {

        try {
            Page<List> page = bnzNewsService.queryNewsList(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page")
                    .addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("queryNewsPage", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

}
