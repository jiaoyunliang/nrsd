package com.rsd.hospital;

import com.github.pagehelper.Page;
import com.rsd.domain.*;
import com.rsd.service.BnzFeedbackService;
import com.rsd.service.BnzInsideMsgService;
import com.rsd.service.BnzOrgInfoService;
import com.rsd.service.BnzSysNoticeService;
import com.rsd.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @data 2019-06-14
 * @modifyUser
 * @modifyDate
 */
@Controller
@RequestMapping("/hospital/sysMsg")
public class SysMsgController {

    private static final Logger logger = LoggerFactory.getLogger(SysMsgController.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BnzInsideMsgService bnzInsideMsgService;


    @Autowired
    private BnzSysNoticeService bnzSysNoticeService;

    @Autowired
    private BnzFeedbackService bnzFeedbackService;

    @Autowired
    private BnzOrgInfoService bnzOrgInfoService;

    @GetMapping(value = "index")
    public String index() {
        return "/hospital/sysMsg/index";
    }

    /**
     * 站内消息 列表
     *
     * @param param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/queryInsideMsgPage")
    public HashMap queryInsideMsgPage(@RequestBody BnzInsideMsgModel param) {

        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            param.setOrgId(account.getOrgId());

            Page page = bnzInsideMsgService.queryMsgPage(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page")
                    .addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("queryInsideMsgPage", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    /**
     * 站内消息 查看
     *
     * @param id 站内消息 id
     * @return
     */
    @GetMapping(value = "queryInsideMsg")
    public String queryInsideMsg(
            @RequestParam(value = "id") Long id,
            Map<String, Object> model) {

        try {
            BnzInsideMsg insideMsg = bnzInsideMsgService.queryInsideMsgById(id);

            BnzMsgReply param = new BnzMsgReply();
            param.setMsgId(id);
            List<BnzMsgReplyModel> replyMsgList = bnzInsideMsgService.queryMsgReplyByMsgId(param);

            model.put("insideMsg", insideMsg);
            model.put("replyMsgList", replyMsgList);
        } catch (Exception e) {
            logger.error("queryInsideMsg", e);
        }

        return "/hospital/sysMsg/replyMsg";
    }

    /**
     * 通知 列表
     *
     * @param param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/queryOrgReadNoticePage")
    public HashMap queryOrgReadNoticePage(@RequestBody BnzSysNoticeModel param) {

        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            param.setOrgId(account.getOrgId());
            param.setSysId(account.getSysId());

            Page page = bnzSysNoticeService.queryOrgReadNoticePage(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page")
                    .addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("queryOrgReadNoticePage", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }


    /**
     * 通知  查看
     *
     * @param id 通知  id
     * @return
     */
    @GetMapping(value = "queryNotice")
    public String queryNotice(
            @RequestParam(value = "id") Long id,
            Map<String, Object> model, HttpSession session) {

        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            BnzNoticeRead param = new BnzNoticeRead();
            param.setAccountId(account.getId());
            param.setNoticeId(id);
            param.setOrgId(account.getOrgId());

            BnzSysNotice notice = bnzSysNoticeService.queryNoticeById(param);

            model.put("notice", notice);

            //更新session通知未读数
            BnzSysNoticeModel param1 = new BnzSysNoticeModel();
            param1.setSysId(account.getSysId());
            param1.setOrgId(account.getOrgId());
            int unreadNum = bnzSysNoticeService.queryNoticeUnreadNum(param1);
            session.setAttribute("noticeUnreadNum", unreadNum);
        } catch (Exception e) {
            logger.error("queryNotice", e);
        }

        return "/hospital/sysMsg/notice";
    }


    /**
     * 添回反馈
     *
     * @param feedback
     * @return
     */
    @ResponseBody
    @PostMapping(value = "addFeedback")
    public HashMap addFeedback(@RequestBody BnzFeedback feedback) {
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            feedback.setAccountId(account.getId());
            feedback.setOrgId(account.getOrgId());
            feedback.setSysId(account.getSysId());
            feedback.setCreateUser(account.getUserName());
            bnzFeedbackService.saveFeedback(feedback);
        } catch (Exception e) {
            logger.error("addFeedback", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addException(messageManager.getMessage("ERROR.0006")).build();
        }
        return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
    }

    @ResponseBody
    @PostMapping(value = "queryOrgList")
    public HashMap queryOrgList(@RequestBody RsdOrgInfo param) {

        try {
            List<Map<String, Object>> list = new ArrayList<>();

            logger.info("传入参数" + param);

            List<RsdOrgInfo> orgInfoList = bnzOrgInfoService.queryOrgList(param);

            if (!orgInfoList.isEmpty()) {
                for (RsdOrgInfo info : orgInfoList) {
                    Map<String, Object> tmp = new HashMap<>();
                    tmp.put("id", info.getId());
                    tmp.put("orgName", info.getOrgName());
                    list.add(tmp);
                }
            }

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("data").addData(list).build();
        } catch (Exception e) {
            logger.error("queryOrgList", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ResponseBody
    @PostMapping(value = "addInsideMsg")
    public HashMap addInsideMsg(@RequestBody BnzInsideMsgModel param) {
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            param.setOrgId(account.getOrgId());
            param.setCreateUser(account.getUserName());

            if (param.getOrgIds().isEmpty()) {
                return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addException(messageManager.getMessage("ERROR.0006")).build();
            }

            bnzInsideMsgService.saveInsideMsg(param);

        } catch (Exception e) {
            logger.error("addInsideMsg", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addException(messageManager.getMessage("ERROR.0006")).build();
        }
        return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
    }

}
