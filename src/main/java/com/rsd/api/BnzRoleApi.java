package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.*;
import com.rsd.domain.RsdRoleModel;
import com.rsd.domain.RsdRole;
import com.rsd.service.BnzResService;
import com.rsd.service.BnzRoleService;
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
import java.util.HashMap;
import java.util.List;

/**
 * @author tony
 * @data 2019-04-01
 * @modifyUser
 * @modifyDate
 */

@Api(value = "角色", description = "角色")
@RestController
@RequestMapping("/bnzRoleApi")
public class BnzRoleApi {

    private static final Logger logger = LoggerFactory.getLogger(BnzRoleApi.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private BnzRoleService bnzRoleService;

    @Autowired
    private BnzResService bnzResService;

    @ApiOperation(value = "queryRoleList", notes = "queryRoleList")
    @PostMapping(value = "/queryRoleList")
    public HashMap queryRoleList(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdRoleModel param) {

        logger.info("传入参数" + param);

        try {
            Page<List> page = bnzRoleService.queryRoleList(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("page").addData(page.getResult()).addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("queryRoleList", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }


    @ApiOperation(value = "queryRoleListBySysId", notes = "queryRoleListBySysId")
    @PostMapping(value = "/queryRoleListBySysId")
    public HashMap queryRoleListBySysId(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdRoleModel param) {

        try {
            List list = bnzRoleService.queryRoleListBySysId(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(list).build();
        } catch (Exception e) {
            logger.error("queryRoleListBySysId", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ApiOperation(value = "queryResByRole", notes = "queryResByRole")
    @PostMapping(value = "/queryResByRole")
    public HashMap queryResByRole(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdRoleModel param) {
        logger.info("传入参数" + param);

        try {
            List listKey = new ArrayList();
            RsdRes res = new RsdRes();
            res.setSysId(param.getSysId());
            if (param.getSysId() == 1) {
                res.setAdminFlag(1);
            } else if (param.getSysId() == 2) {
                res.setHospitalFlag(1);
            } else if (param.getSysId() == 3) {
                res.setOrgFlag(1);
            }
            List<RsdRes> resList = bnzResService.queryResList(res);

            if (param.getId() != null) {
                List<RsdRes> list = bnzResService.queryResListByRoleId(param);

                if (!list.isEmpty()) {
                    for (RsdRes tmp : list) {
                        listKey.add(tmp.getId());
                    }
                }
            }
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(resList).addData("resList", listKey).build();
        } catch (Exception e) {
            logger.error("queryResByRole", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }

    @ApiOperation(value = "saveRole", notes = "saveRole")
    @PostMapping(value = "/saveRole")
    public HashMap saveRole(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdRoleModel param) {
        logger.info("传入参数" + param);
        try {
            RsdAccountModel account = sessionManager.getCurrentUser();

            param.setAccountId(account.getId());

            bnzRoleService.saveRole(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("saveRole", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }


    @ApiOperation(value = "updateRoleStatus", notes = "updateRoleStatus")
    @PostMapping(value = "/updateRoleStatus")
    public HashMap updateRoleStatus(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdRoleModel param) {
        logger.info("传入参数" + param);

        try {
            RsdRole role = new RsdRole();
            role.setId(param.getId());
            role.setIsDel(param.getIsDel());

            bnzRoleService.updateRole(role);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("updateRoleStatus", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

}
