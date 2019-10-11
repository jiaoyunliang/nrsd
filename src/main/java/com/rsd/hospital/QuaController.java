package com.rsd.hospital;

import com.github.pagehelper.Page;
import com.rsd.domain.RsdAccount;
import com.rsd.domain.BnzFileRelation;
import com.rsd.domain.BnzInstitutionQuaModel;
import com.rsd.domain.BnzProductQuaModel;
import com.rsd.service.BnzInstitutionQuaService;
import com.rsd.service.BnzProductQuaService;
import com.rsd.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
 * @Description 医院端资质管理
 * @Date 2019/6/11 16:15
 * @Version 1.0
 **/
@PropertySource(value = {"classpath:upload.properties"}, encoding = "utf-8")
@Controller
@RequestMapping("hospital")
public class QuaController {
    private static final Logger logger = LoggerFactory.getLogger(QuaController.class);
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private BnzInstitutionQuaService hospitalService;
    @Autowired
    private BnzProductQuaService productQuaService;
    @Autowired
    private SessionManager sessionManager;
    @Value("${ueditorFileUrlPerfix}")
    private String fileUrlPrefix;

    /**
     * @author hdx
     * @Description 跳转到院内资质页面
     * @Date 2019/6/12 10:01
     * @Param [model]
     * @Return java.lang.String
     */
    @RequestMapping(value = "/qua/list")
    public String list(Map<String, Object> model) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        return "/hospital/qua/maintab";
    }

    /**
     * @author hdx
     * @Description 跳转到全网资质页面
     * @Date 2019/6/12 10:02
     * @Param [model]
     * @Return java.lang.String
     */
    @RequestMapping(value = "/qua/alllist")
    public String alllist(Map<String, Object> model) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        return "/hospital/qua/maintab";
    }

    /**
     * @author hdx
     * @Description 院内资质跳转到企业资质详细信息页面
     * @Date 2019/6/12 10:03
     * @Param
     * @Return
     */
    @RequestMapping(value = "/qua/hospitalquadetail")
    public String hospitalquadetail(Map<String, Object> model, @RequestParam(value = "quaType") String quaType, @RequestParam(value = "quaName") String quaName) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        model.put("quaName", quaName);
        model.put("quaType", quaType);//企业资质类型如注册证为字典编码
        model.put("serverUrl", fileUrlPrefix);//图片服务器地址
        return "/hospital/qua/hospitalquadetail";
    }

    /**
     * @author hdx
     * @Description 院内资质跳转到产品资质详细信息页面
     * @Date 2019/6/12 10:03
     * @Param
     * @Return
     */
    @RequestMapping(value = "/qua/productquadetail")
    public String productquadetail(Map<String, Object> model, @RequestParam(value = "quaName") String quaName, @RequestParam(value = "quaCode") String quaCode) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        model.put("quaName", quaName);
        model.put("serverUrl", fileUrlPrefix);//图片服务器地址
        model.put("quaCode", quaCode);//企业资质类型（字典：如注册证等）
        return "/hospital/qua/productquadetail";
    }


    /**
     * @author hdx
     * @Description 全网资质跳转到产品资质详细信息页面
     * @Date 2019/6/12 10:03
     * @Param
     * @Return
     */
    @RequestMapping(value = "/qua/allproductquadetail")
    public String allproductquadetail(Map<String, Object> model, @RequestParam(value = "quaName") String quaName, @RequestParam(value = "quaCode") String quaCode, @RequestParam(value = "orgInfoId") String orgInfoId) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        model.put("quaName", quaName);
        model.put("orgInfoId", orgInfoId);//要查询资质的机构编号
        model.put("serverUrl", fileUrlPrefix);//图片服务器地址
        model.put("quaCode", quaCode);//企业资质类型（字典：如注册证等）
        return "/hospital/qua/allproductquadetail";
    }


    /**
     * @author hdx
     * @Description 全网资质跳转到企业资质详细信息页面
     * @Date 2019/6/12 10:03
     * @Param
     * @Return
     */
    @RequestMapping(value = "/qua/allhospitalquadetail")
    public String allhospitalquadetail(Map<String, Object> model, @RequestParam(value = "quaType") String quaType, @RequestParam(value = "quaName") String quaName, @RequestParam(value = "orgInfoId") String orgInfoId) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        model.put("quaName", quaName);
        model.put("orgInfoId", orgInfoId);
        model.put("quaType", quaType);//企业资质类型如注册证为字典编码
        model.put("serverUrl", fileUrlPrefix);//图片服务器地址
        return "/hospital/qua/allhospitalquadetail";
    }

    /**
     * @author hdx
     * @Description 全网资质跳转
     * @Date 2019/6/12 10:03
     * @Param
     * @Return
     */
    @RequestMapping(value = "/qua/allqua")
    public String allqua(Map<String, Object> model) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        model.put("quaType", 2);//设置默认查询产品资质
        return "/hospital/qua/allproductqua";
    }

    /**
     * @author hdx
     * @Description 全网资质跳转
     * @Date 2019/6/12 10:03
     * @Param
     * @Return
     */
    @RequestMapping(value = "/qua/searchallqua")
    public String searchallqua(Map<String, Object> model, @RequestParam(value = "quaType") String quaType, @RequestParam(value = "quaCode") String quaCode, @RequestParam(value = "orgName") String orgName) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        if (quaType == null) {
            quaType = "2";
        } else {
            if ("".equals(quaType)) {
                quaType = "2";
            }
        }
        model.put("time", new Date());
        model.put("account", account);
        model.put("quaType", quaType);//设置默认查询产品资质
        model.put("quaCode", quaCode);
        model.put("orgName", orgName);
        return "/hospital/qua/allproductqua";
    }

    /**
     * @author hdx
     * @Description 查询企业资质数量列表
     * @Date 2019/6/11 16:34
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping("/qua/hospitalquainfo")
    @ResponseBody
    public HashMap<String, ?> hospitalquainfo(@RequestBody BnzInstitutionQuaModel bnzInstitutionQuaModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            bnzInstitutionQuaModel.setOrgInfoId(account.getOrgId());
            Page<List<BnzInstitutionQuaModel>> page = hospitalService.searchBnzInstitutionQuaList(bnzInstitutionQuaModel);
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

    /**
     * @author hdx
     * @Description 查询产品资质数量
     * @Date 2019/6/12 9:18
     * @Param [bnzProductQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping("/qua/productquainfo")
    @ResponseBody
    public HashMap<String, ?> productqualist(@RequestBody BnzProductQuaModel bnzProductQuaModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            bnzProductQuaModel.setOrgInfoId(account.getOrgId());
            Page<List<BnzProductQuaModel>> page = productQuaService.searchBnzProductQuaList(bnzProductQuaModel);
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

    /**
     * @author hdx
     * @Description 企业资质详细列表查询
     * @Date 2019/6/12 14:08
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping(value = "/qua/hospitalquadetailinfo")
    @ResponseBody
    public HashMap<String, ?> institutiondetail(@RequestBody BnzInstitutionQuaModel bnzInstitutionQuaModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            if (bnzInstitutionQuaModel.getOrgInfoId() == null || "".equals(bnzInstitutionQuaModel.getOrgInfoId())) {
                RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
                bnzInstitutionQuaModel.setOrgInfoId(account.getOrgId());
            }
            Page<List<BnzInstitutionQuaModel>> page = hospitalService.searchBnzInstitutionQuaDetailList(bnzInstitutionQuaModel);
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

    /**
     * @author hdx
     * @Description 删除医院端维护企业资质数据
     * @Date 2019/6/12 17:02
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping(value = "/qua/deletehospitalquadetailinfo")
    @ResponseBody
    public HashMap<String, ?> deletehospitalquadetailinfo(@RequestBody BnzInstitutionQuaModel bnzInstitutionQuaModel) {
        JsonOut<List<BnzInstitutionQuaModel>> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            bnzInstitutionQuaModel.setUpdateUser(account.getUserName());
            hospitalService.deleteBnzInstitutionDetailById(bnzInstitutionQuaModel);
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

    /**
     * @author hdx
     * @Description 新增医院端维护企业资质数据
     * @Date 2019/6/12 17:02
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping(value = "/qua/inserthospitalquadetailinfo")
    @ResponseBody
    public HashMap<String, ?> inserthospitalquadetailinfo(@RequestBody BnzInstitutionQuaModel bnzInstitutionQuaModel) {
        JsonOut<List<BnzInstitutionQuaModel>> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            bnzInstitutionQuaModel.setCreateUser(account.getUserName());
            bnzInstitutionQuaModel.setUpdateUser(account.getUserName());
            bnzInstitutionQuaModel.setTypeFlag(2);//设置维护机构类型为医院
            bnzInstitutionQuaModel.setIsDel(0);//设置删除状态为未删除
            bnzInstitutionQuaModel.setIsShow(0);//医院端维护资质默认设置为全网不可见
            bnzInstitutionQuaModel.setOrgInfoId(account.getOrgId());
            hospitalService.insertBnzInstitutionDetail(bnzInstitutionQuaModel);
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

    /**
     * @author hdx
     * @Description 更新医院端维护企业资质数据
     * @Date 2019/6/12 17:02
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping(value = "/qua/upadtehospitalquadetailinfo")
    @ResponseBody
    public HashMap<String, ?> upadtehospitalquadetailinfo(@RequestBody BnzInstitutionQuaModel bnzInstitutionQuaModel) {
        JsonOut<List<BnzInstitutionQuaModel>> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            bnzInstitutionQuaModel.setUpdateUser(account.getUserName());
            hospitalService.updateBnzInstitutionDetail(bnzInstitutionQuaModel);
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

    /**
     * @author hdx
     * @Description 查询全网资质瀑布流列表
     * @Date 2019/6/26 10:32
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap
     */
    @ResponseBody
    @RequestMapping(value = "/qua/institution/list")
    public HashMap list(@RequestBody BnzInstitutionQuaModel bnzInstitutionQuaModel) {
        try {
            bnzInstitutionQuaModel.setIsShow(1);//是否全网可见设置为可见
            bnzInstitutionQuaModel.setTypeFlag(1);//企业维护
            bnzInstitutionQuaModel.setOrgInfoType("2");//设置机构类型
            Page page = hospitalService.searchBnzInstitutionQuaDetailList(bnzInstitutionQuaModel);
            List<BnzInstitutionQuaModel> list = page.getResult();
            if (!list.isEmpty()) {
                for (BnzInstitutionQuaModel od : list) {
                    // logo
                    List<BnzFileRelation> quaList = od.getBnzFileRelation();
                    if (!quaList.isEmpty()) {
                        od.setQuaPic(quaList.get(0).getFileUrl());
                    }
                }
            }
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("data").addData(page.getResult())
                    .addData("fileServer", fileUrlPrefix)
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();

        } catch (Exception e) {
            logger.error("EnterpriseController->list", e.getMessage());
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addMessage(messageManager.getMessage("ERROR.0006")).build();
        }
    }
}
