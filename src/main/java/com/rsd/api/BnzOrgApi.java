package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.*;
import com.rsd.domain.RsdOrgInfoModel;
import com.rsd.domain.RsdOrgInfo;
import com.rsd.service.BnzOrgInfoService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author tony
 * @data 2019-04-02
 * @modifyUser
 * @modifyDate
 */
@Api(value = "org", description = "org")
@RestController
@RequestMapping("/bnzOrgApi")
public class BnzOrgApi {

    private static final Logger logger = LoggerFactory.getLogger(BnzOrgApi.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private BnzOrgInfoService bnzOrgInfoService;

    @ApiOperation(value = "queryOrgList", notes = "queryOrgList")
    @PostMapping(value = "/queryOrgList")
    public HashMap queryOrgList(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdOrgInfo param) {

        logger.info("传入参数" + param);

        try {
            List<RsdOrgInfo> list = bnzOrgInfoService.queryOrgList(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(list).build();
        } catch (Exception e) {
            logger.error("queryOrgList", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }

    @ApiOperation(value = "saveOrg", notes = "saveOrg")
    @PostMapping(value = "/saveOrg")
    public HashMap saveOrg(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdOrgInfoModel param) {
        logger.info("传入参数" + param);

        try {
            RsdAccountModel account = sessionManager.getCurrentUser();
            if (param.getId() == null) {
                param.setCreateUser(account.getUserName());
                param.setJoinDate(new Date());
                bnzOrgInfoService.saveOrg(param);
            } else {
                param.setUpdateUser(account.getUserName());
                param.setUpdateTime(new Date());
                bnzOrgInfoService.updateOrg(param);

            }
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("saveOrg", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ApiOperation(value = "queryOrgPage", notes = "queryOrgPage")
    @PostMapping(value = "/queryOrgPage")
    public HashMap queryOrgPage(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdOrgInfoModel param) {

        try {
            Page<List> page = bnzOrgInfoService.queryOrgPage(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page")
                    .addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("queryOrgPage", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ApiOperation(value = "queryOrgTypes", notes = "queryOrgTypes")
    @PostMapping(value = "/queryOrgTypes")
    public HashMap queryOrgTypes(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdOrgInfoModel param) {

        logger.info("传入参数" + param);

        try {
            List<Long> orgTypeIds = new ArrayList<>();

            List<BnzProductType> list = bnzOrgInfoService.queryOrgTypes(param);

            if (!list.isEmpty()) {
                for (BnzProductType t : list) {
                    orgTypeIds.add(t.getId());
                }
            }
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(orgTypeIds).build();
        } catch (Exception e) {
            logger.error("queryOrgTypes", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }

}
