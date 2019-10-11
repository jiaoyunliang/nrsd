package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.*;
import com.rsd.service.BnzFileRelationService;
import com.rsd.service.BnzProductService;
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
import java.util.Map;

/**
 * @author tony
 * @data 2019-05-13
 * @modifyUser
 * @modifyDate
 */
@Api(value = "产品管理", description = "产品管理")
@RestController
@RequestMapping("/bnzProductApi")
public class BnzProductApi {

    private static final Logger logger = LoggerFactory.getLogger(BnzProductApi.class);

    @Autowired
    private MessageManager messageManager;


    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private BnzProductService bnzProductService;

    @Autowired
    private BnzFileRelationService bnzFileRelationService;

    @ApiOperation(value = "queryProductPage", notes = "queryProductPage")
    @PostMapping(value = "/queryProductPage")
    public HashMap queryProductPage(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzProductModel param) {

        try {
            Page<List> page = bnzProductService.queryProductPage(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page").addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("queryProductPage", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ApiOperation(value = "updateProduct", notes = "updateProduct")
    @PostMapping(value = "/updateProduct")
    public HashMap updateProduct(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzProduct param) {
        logger.info("传入参数" + param);
        try {
            RsdAccountModel account = sessionManager.getCurrentUser();
            param.setUpdateTime(new Date());
            param.setUpdateUser(account.getUserName());

            bnzProductService.updateProduct(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("updateProduct", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }


    @ApiOperation(value = "queryProduct", notes = "queryProduct")
    @PostMapping(value = "/queryProduct")
    public HashMap queryProduct(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzProductModel param) {

        try {
            Map<String, Object> map = new HashMap<>();

            //ProductPrice
            List<BnzProductPrice> priceList = bnzProductService.queryProductPriceByProductId(param.getId());
            map.put("priceList", priceList);
            // pic
            List<BnzFileRelation> picList = bnzFileRelationService.searchFileByCategoryId(5, param.getId());
            if (!picList.isEmpty()) {
                map.put("picList", picList);
            } else {
                map.put("picList", null);
            }

            // video
            List<BnzFileRelation> list = bnzFileRelationService.searchFileByCategoryId(6, param.getId());
            if (!list.isEmpty()) {
                map.put("video", list.get(0));
            } else {
                map.put("video", null);
            }
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(map).build();

        } catch (Exception e) {
            logger.error("queryProduct", e.getMessage());
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }
}
