package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzFileRelation;
import com.rsd.domain.BnzOrgDetailModel;
import com.rsd.service.BnzFileRelationService;
import com.rsd.service.BnzOrgDetailService;
import com.rsd.utils.Const;
import com.rsd.utils.JsonOut;
import com.rsd.utils.MessageManager;
import com.rsd.utils.PageInfoWrap;
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
import java.util.Map;

/**
 * @author tony
 * @data 2019-05-05
 * @modifyUser
 * @modifyDate
 */
@Api(value = "企业详情", description = "企业详情")
@RestController
@RequestMapping("/bnzOrgDetailApi")
public class BnzOrgDetailApi {


    private static final Logger logger = LoggerFactory.getLogger(BnzOrgDetailApi.class);

    @Autowired
    private BnzOrgDetailService bnzOrgDetailService;

    @Autowired
    private BnzFileRelationService bnzFileRelationService;


    @Autowired
    private MessageManager messageManager;


    @ApiOperation(value = "queryOrgDetailPage", notes = "queryOrgDetailPage")
    @PostMapping(value = "/queryOrgDetailPage")
    public HashMap queryOrgDetailPage(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzOrgDetailModel param) {

        try {
            Page<List> page = bnzOrgDetailService.queryOrgDetailPage(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page").addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("queryOrgDetailPage", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ApiOperation(value = "queryOrgDetailPics", notes = "queryOrgDetailPics")
    @PostMapping(value = "/queryOrgDetailPics")
    public HashMap queryOrgDetailPics(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzOrgDetailModel param) {

        try{

            Map<String,Object> map = new HashMap<>();
            // logo
            List<BnzFileRelation> list = bnzFileRelationService.searchFileByCategoryId(3,param.getOrgId());
            if(!list.isEmpty()){
                BnzFileRelation logo = list.get(0);
                map.put("logo",logo);
            } else {
                map.put("logo",null);
            }

            // brandPics
            List<BnzFileRelation> brandPics = bnzFileRelationService.searchFileByCategoryId(4,param.getOrgId());
            if(!brandPics.isEmpty()){
                map.put("brandPics",brandPics);
            } else {
                map.put("brandPics",null);
            }

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(map).build();
        }catch (Exception e){
            logger.error("queryOrgDetailPics",e.getMessage());
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }


}
