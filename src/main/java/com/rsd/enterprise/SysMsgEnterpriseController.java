package com.rsd.enterprise;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tony
 * @data 2019-06-27
 * @modifyUser
 * @modifyDate
 */
@Controller
@RequestMapping("/enterprise/sysMsg")
public class SysMsgEnterpriseController {

    private static final Logger logger = LoggerFactory.getLogger(SysMsgEnterpriseController.class);

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
        return "/enterprise/sysMsg/index";
    }

    /**
     * 站内消息 列表
     *
     * @param param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "queryInsideMsgPage")
    public HashMap queryInsideMsgPage(@RequestBody BnzInsideMsgModel param) {

        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            param.setOrgId(account.getOrgId());

            Page page = bnzInsideMsgService.queryMsgByOrgPage(param);

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
     * 查站内消息及回复
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "queryInsideMsg")
    public String queryInsideMsg(
            @RequestParam(value = "id") Long id,
            Map<String, Object> model){
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            BnzInsideMsg msg = bnzInsideMsgService.queryInsideMsgById(id);
            BnzMsgReply param = new BnzMsgReply();
            param.setMsgId(id);
            param.setOrgId(account.getOrgId());
            BnzMsgReplyModel replyMsg = bnzInsideMsgService.queryMsgReply(param);

            model.put("msg",msg);
            model.put("replyMsg",replyMsg);
        }catch (Exception e){
            logger.error("queryInsideMsg", e);
        }
        return "/enterprise/sysMsg/insideMsg";
    }


    @ResponseBody
    @PostMapping(value = "saveReplyMsg")
    public HashMap saveReplyMsg(@RequestBody BnzMsgReply param) {
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            param.setReplyTime(new Date());
            param.setReplyUser(account.getUserName());
            bnzInsideMsgService.updateMsgReply(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("saveReplyMsg", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
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

        return "/enterprise/sysMsg/notice";
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
}
