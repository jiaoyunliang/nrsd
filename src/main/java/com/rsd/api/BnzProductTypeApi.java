package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.RsdAccountModel;
import com.rsd.domain.BnzProductType;
import com.rsd.domain.BnzProductTypeModel;
import com.rsd.service.BnzProductTypeService;
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

/**
 * @author tony
 * @data 2019-04-23
 * @modifyUser
 * @modifyDate
 */
@Api(value = "产品类型", description = "产品类型")
@RestController
@RequestMapping("/bnzProductTypeApi")
public class BnzProductTypeApi {

    private static final Logger logger = LoggerFactory.getLogger(BnzProductTypeApi.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private BnzProductTypeService bnzProductTypeService;

    @ApiOperation(value = "saveProductType", notes = "saveProductType")
    @PostMapping(value = "/saveProductType")
    public HashMap saveProductType(@RequestBody BnzProductType productType) {
        try {
            RsdAccountModel account = sessionManager.getCurrentUser();
            productType.setCreateUser(account.getUserName());
            bnzProductTypeService.saveProductType(productType);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("saveProductType", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ApiOperation(value = "updateProductType", notes = "updateProductType")
    @PostMapping(value = "/updateProductType")
    public HashMap updateProductType(@RequestBody BnzProductType productType) {
        try {
            RsdAccountModel account = sessionManager.getCurrentUser();
            productType.setUpdateTime(new Date());
            productType.setUpdateUser(account.getUserName());
            productType.setCreateTime(null);
            bnzProductTypeService.updateProductType(productType);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("updateProductType", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ApiOperation(value = "queryProductType", notes = "queryProductType")
    @PostMapping(value = "/queryProductType")
    public HashMap queryProductType(@RequestBody BnzProductTypeModel param) {

        try {
            Page<List> page = bnzProductTypeService.queryProductType(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page").addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("queryProductType", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ApiOperation(value = "queryOrgType", notes = "queryOrgType")
    @PostMapping(value = "/queryOrgType")
    public HashMap queryOrgType(@RequestBody BnzProductTypeModel param) {

        try {
            List list = bnzProductTypeService.queryOrgType();
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(list).build();
        } catch (Exception e) {
            logger.error("queryOrgType", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }
}
