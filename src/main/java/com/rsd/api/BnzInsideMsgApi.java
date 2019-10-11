package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzInsideMsgModel;
import com.rsd.domain.BnzMsgReply;
import com.rsd.domain.BnzMsgReplyModel;
import com.rsd.service.BnzInsideMsgService;
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

/**
 * @author tony
 * @data 2019-05-06
 * @modifyUser
 * @modifyDate
 */
@Api(value = "站内消息", description = "站内消息")
@RestController
@RequestMapping("/bnzInsideMsgApi")
public class BnzInsideMsgApi {

    private static final Logger logger = LoggerFactory.getLogger(BnzInsideMsgApi.class);

    @Autowired
    private BnzInsideMsgService bnzInsideMsgService;

    @Autowired
    private MessageManager messageManager;

    @ApiOperation(value = "queryMsgPage", notes = "queryMsgPage")
    @PostMapping(value = "/queryMsgPage")
    public HashMap queryMsgPage(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzInsideMsgModel param) {

        try {
            Page<List> page = bnzInsideMsgService.queryMsgPage(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page").addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("queryMsgPage", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ApiOperation(value = "queryMsgReplyByMsgId", notes = "queryMsgReplyByMsgId")
    @PostMapping(value = "/queryMsgReplyByMsgId")
    public HashMap queryMsgReplyByMsgId(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzMsgReply param) {

        logger.info("传入参数" + param);

        try {
            List<BnzMsgReplyModel> list = bnzInsideMsgService.queryMsgReplyByMsgId(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(list).build();
        } catch (Exception e) {
            logger.error("queryMsgReplyByMsgId", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }

}
