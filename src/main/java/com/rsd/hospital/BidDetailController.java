package com.rsd.hospital;

import com.github.pagehelper.Page;
import com.rsd.api.BnzBidDetailApi;
import com.rsd.domain.RsdAccount;
import com.rsd.domain.BnzBidDetailModel;
import com.rsd.service.BnzBidDetailService;
import com.rsd.service.BnzBidShowColumnService;
import com.rsd.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @ClassName hdx
 * @Description TODO
 * @Date 2019/7/5 16:50
 * @Version 1.0
 **/
@Controller
@RequestMapping("/hospital")
public class BidDetailController {
    private static final Logger logger = LoggerFactory.getLogger(BnzBidDetailApi.class);
    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BnzBidShowColumnService bnzBidShowColumnService;

    @Autowired
    private BnzBidDetailService bnzBidDetailService;

    @Autowired
    private SessionManager sessionManager;


    @RequestMapping(value = "/bid/list")
    public String list(Map<String, Object> model, @RequestParam(value = "projectId") String projectId) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        model.put("projectId", projectId);
        return "/hospital/project/biddetail";
    }


    @RequestMapping(value = "/bid/queryBidDetailPage")
    @ResponseBody
    public HashMap<String, ?> queryNewsPage(@RequestBody BnzBidDetailModel param) {
        try {
            Page<List> page = this.bnzBidDetailService.queryBidDetail(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page")
                    .addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }
}
