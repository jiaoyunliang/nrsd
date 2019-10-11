package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzInstitutionQuaModel;
import com.rsd.service.BnzInstitutionQuaService;
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
 * @author admin
 * @ClassName hdx
 * @Description 机构资质类型控制类
 * @Date 2019/5/13 11:11
 * @Version 1.0
 **/
@RestController
@RequestMapping("/institution")
public class BnzInstitutionQuaApi {
    private static final Logger logger = LoggerFactory.getLogger(BnzOrgApi.class);
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private BnzInstitutionQuaService service;
    @Autowired
    private SessionManager sessionManager;

    @RequestMapping("/list")
    @ResponseBody
    public HashMap<String, ?> list(@RequestBody BnzInstitutionQuaModel bnzInstitutionQuaModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            //bnzInstitutionQuaModel.setOrgInfoType("3");//设置查询机构类型为企业字典对应值为3
            Page<List<BnzInstitutionQuaModel>> page = service.searchBnzInstitutionList(bnzInstitutionQuaModel);
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

    @RequestMapping("institutionlist")
    @ResponseBody
    public HashMap<String, ?> institutionlist(@RequestBody BnzInstitutionQuaModel bnzInstitutionQuaModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            Page<List<BnzInstitutionQuaModel>> page = service.searchBnzInstitutionQuaList(bnzInstitutionQuaModel);
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

    @RequestMapping("institutiondetail")
    @ResponseBody
    public HashMap<String, ?> institutiondetail(@RequestBody BnzInstitutionQuaModel bnzInstitutionQuaModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            Page<List<BnzInstitutionQuaModel>> page = service.searchBnzInstitutionQuaDetailList(bnzInstitutionQuaModel);
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

