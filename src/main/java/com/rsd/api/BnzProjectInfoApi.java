package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.RsdAccountModel;
import com.rsd.domain.BnzProjectInfo;
import com.rsd.domain.BnzProjectInfoModel;
import com.rsd.service.BnzProjectInfoService;
import com.rsd.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author hdx
 * @Description 项目控制类
 * @Date 15:51 2019/4/22
 */
@RestController
@RequestMapping("/project")
public class BnzProjectInfoApi {
    private static final Logger logger = LoggerFactory.getLogger(BnzOrgApi.class);
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private BnzProjectInfoService bnzProjectInfoService;
    @Autowired
    private SessionManager sessionManager;

    @RequestMapping("/list")
    @ResponseBody
    public HashMap<String, ?> searchProjectInfo(@RequestBody BnzProjectInfoModel bnzProjectInfoModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
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

    @RequestMapping("/add")
    @ResponseBody
    public HashMap<String, ?> addProjectInfo(@RequestBody BnzProjectInfoModel bnzProjectInfoModel) {
        JsonOut<List<BnzProjectInfo>> jsonOut = new JsonOut();
        try {
            RsdAccountModel account = sessionManager.getCurrentUser();
            bnzProjectInfoModel.setCreateUser(account.getUserName());

            if(null == bnzProjectInfoModel.getId()){
                bnzProjectInfoService.addProjectInfo(bnzProjectInfoModel);
            }else{
                bnzProjectInfoService.updateProjectInfo(bnzProjectInfoModel);
            }
            jsonOut.addMessage(messageManager.getMessage("ERROR.0005"));
            return jsonOut.build();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
            jsonOut.addResult(Const.ApiResult.EXCEPTION);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0006"));
            jsonOut.addException(ex.getMessage());
            return jsonOut.build();
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public HashMap<String, ?> deleteProjectInfo(@RequestBody BnzProjectInfoModel bnzProjectInfoModel) {
        JsonOut<List<BnzProjectInfo>> jsonOut = new JsonOut();
        try {
            RsdAccountModel account = sessionManager.getCurrentUser();
            bnzProjectInfoModel.setUpdateUser(account.getUserName());
            bnzProjectInfoService.deleteProjectInfo(bnzProjectInfoModel);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0005"));
            return jsonOut.build();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
            jsonOut.addResult(Const.ApiResult.EXCEPTION);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0006"));
            jsonOut.addException(ex.getMessage());
            return jsonOut.build();
        }
    }
}
