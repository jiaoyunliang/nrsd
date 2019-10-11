package com.rsd.hospital;

import com.github.pagehelper.Page;
import com.rsd.api.BnzOrgApi;
import com.rsd.domain.BnzProjectInfoModel;
import com.rsd.domain.RsdAccount;
import com.rsd.service.BnzProjectInfoService;
import com.rsd.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @ClassName hdx
 * @Description 项目控制类
 * @Date 2019/6/3 16:30
 * @Version 1.0
 **/
@Controller
@RequestMapping("hospital")
public class ProjectController {
    private static final Logger logger = LoggerFactory.getLogger(BnzOrgApi.class);
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private BnzProjectInfoService bnzProjectInfoService;
    @Autowired
    private SessionManager sessionManager;

    @RequestMapping(value = "/project/list")
    public String index(Map<String, Object> model) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        return "/hospital/project/maintab";
    }

    @RequestMapping("/project/listinfo")
    @ResponseBody
    public HashMap<String, ?> searchProjectInfo(@RequestBody BnzProjectInfoModel bnzProjectInfoModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            if ("1".equals(bnzProjectInfoModel.getProjectType())) {
                RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
                bnzProjectInfoModel.setOrgId(account.getOrgId());
            }
            Page<List<BnzProjectInfoModel>> page = bnzProjectInfoService.searchProjectInfoList(bnzProjectInfoModel);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0005"));
            jsonOut.addDataName("page");
            jsonOut.addData(page.getResult()).addData("pageInfo", new PageInfoWrap(page).get());
            return jsonOut.build();
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonOut.addResult(Const.ApiResult.EXCEPTION);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0006"));
            jsonOut.addException(ex.getMessage());
            return jsonOut.build();
        }
    }
}
