package com.rsd.enterprise;

import com.github.pagehelper.Page;
import com.rsd.domain.*;
import com.rsd.service.BnzInstitutionQuaService;
import com.rsd.service.BnzPackageRecordService;
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

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author admin
 * @ClassName hdx
 * @Description 医院资质控制
 * @Date 2019/6/27 13:17
 * @Version 1.0
 **/
@PropertySource(value = {"classpath:upload.properties"}, encoding = "utf-8")
@Controller
@RequestMapping("/enterprise")
public class EnterpriseHospitalQuaController {
    private static final Logger logger = LoggerFactory.getLogger(IndexEnterpriseController.class);

    @Autowired
    private MessageManager messageManager;
    @Autowired
    private BnzInstitutionQuaService hospitalService;
    @Autowired
    private BnzPackageRecordService bnzPackageRecordService;

    @Value("${ueditorFileUrlPerfix}")
    private String fileUrlPrefix;
    @Value("${global_static_file_location}")
    private String filePath;
    @Value("${file_dir}")
    private String fileDir;

    /**
     * @author hdx
     * @Description 跳转到企业资质页面
     * @Date 2019/6/12 10:01
     * @Param [model]
     * @Return java.lang.String
     */
    @RequestMapping(value = "/qua/hospitallist")
    public String hospitallist(Map<String, Object> model) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        return "/enterprise/qua/hospitallist";
    }

    /**
     * @author hdx
     * @Description 跳转到企业资质详细页面
     * @Date 2019/6/12 10:01
     * @Param [model]
     * @Return java.lang.String
     */
    @RequestMapping(value = "/qua/quadetaillist")
    public String enterprisequadetailinfo(Map<String, Object> model, @RequestParam(value = "quaType") String quaType, @RequestParam(value = "quaName") String quaName) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        model.put("quaName", quaName);
        model.put("quaType", quaType);//企业资质类型如注册证为字典编码
        model.put("serverUrl", fileUrlPrefix);//图片服务器地址
        return "/enterprise/qua/hospitalquadetail";
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
            bnzInstitutionQuaModel.setTypeFlag(1);//设置查询条件为企业端维护
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
     * @Description 企业资质详细列表查询
     * @Date 2019/6/12 14:08
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping(value = "/qua/hospitalquadetailinfo")
    @ResponseBody
    public HashMap<String, ?> hospitalquadetailinfo(@RequestBody BnzInstitutionQuaModel bnzInstitutionQuaModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            bnzInstitutionQuaModel.setOrgInfoId(account.getOrgId());
            bnzInstitutionQuaModel.setTypeFlag(1);//设置查询条件为企业维护
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
     * @Description 删除企业端维护企业资质数据
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
            RsdOrgInfo rsdOrgInfo = HttpSessionManager.get(Const.SESSION_ACCOUNT_ORG, RsdOrgInfo.class);
            bnzInstitutionQuaModel.setCreateUser(account.getUserName());
            bnzInstitutionQuaModel.setUpdateUser(account.getUserName());
            bnzInstitutionQuaModel.setTypeFlag(1);//设置维护机构类型为企业
            bnzInstitutionQuaModel.setIsDel(0);//设置删除状态为未删除
            bnzInstitutionQuaModel.setOrgName(rsdOrgInfo.getOrgName());//设置企业端维护默认为登陆用户所属机构名称
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

    @RequestMapping(value = "/qua/doPackage")
    @ResponseBody
    public HashMap<String, ?> doPackage(@RequestBody List<BnzPackageRecordModel> bnzPackageRecordModelList) {
        JsonOut<List<BnzInstitutionQuaModel>> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            BnzInstitutionQuaModel bnzInstitutionQuaModel = new BnzInstitutionQuaModel();
            bnzInstitutionQuaModel.setOrgInfoId(account.getOrgId());
            bnzInstitutionQuaModel.setTypeFlag(1);//企业维护
            List<BnzInstitutionQuaModel> list = hospitalService.searchPackRecrodInstitutionList(bnzInstitutionQuaModel);
            List<ElementBean> beans = new ArrayList<ElementBean>();
            if (list != null) {
                for (BnzInstitutionQuaModel bean : list
                ) {
                    List<BnzFileRelation> bnzFileRelations = bean.getBnzFileRelation();
                    for (BnzFileRelation bnzFileRelation :
                            bnzFileRelations) {
                        ElementBean elementBean = new ElementBean();
                        elementBean.setContent(filePath + bnzFileRelation.getFileUrl());
                        elementBean.setType(ElementBean.TYPE_IMAGE);
                        beans.add(elementBean);
                    }
                }
            }
            if (!beans.isEmpty()) {
                BnzPackageRecordModel bnzPackageRecord = new BnzPackageRecordModel();
                Date createDate = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                String pdf, zip;
                bnzPackageRecord.setType(1);//设置打包记录类型为企业资质
                pdf = fileDir + "qyzz" + sf.format(createDate) + ".pdf";
                zip = fileDir + "qyzz" + sf.format(createDate) + ".zip";
                PdfCreator.createPdf(beans, filePath + pdf);
                ZipUtils.zipSingleFile(filePath + pdf, filePath + zip);
                bnzPackageRecord.setCreateUser(account.getUserName());
                bnzPackageRecord.setOrgInfoId(account.getOrgId());
                bnzPackageRecord.setPackageUrl(zip);
                bnzPackageRecordService.insert(bnzPackageRecord);
                jsonOut.addMessage(messageManager.getMessage("ERROR.0005")).addDataName("url").addData(fileUrlPrefix+bnzPackageRecord.getPackageUrl());
            }
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
     * @Description 打包资质列表查询
     * @Date 2019/6/12 14:08
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping(value = "/qua/packageList")
    @ResponseBody
    public HashMap<String, ?> packageList(@RequestBody BnzPackageRecordModel bnzPackageRecordModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            bnzPackageRecordModel.setOrgInfoId(account.getOrgId());
            bnzPackageRecordModel.setType(1);//企业资质
            Page<List<BnzPackageRecordModel>> page = bnzPackageRecordService.findPackageRecordPage(bnzPackageRecordModel);
            if(page!=null){
                List list=page.getResult();
                for (Object object: list
                ) {
                    BnzPackageRecord bnzPackageRecord=(BnzPackageRecord)object;
                    String packageUrl=bnzPackageRecord.getPackageUrl();
                    bnzPackageRecord.setPackageUrl(fileUrlPrefix+packageUrl);
                }
            }
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
     * @Description 打包资质列表查询
     * @Date 2019/6/12 14:08
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping(value = "/qua/packageListState")
    @ResponseBody
    public HashMap<String, ?> packageListState(@RequestBody BnzPackageRecordModel bnzPackageRecordModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            List<BnzPackageRecordModel> list = bnzPackageRecordService.selectInstitutionQuaPackState(bnzPackageRecordModel);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).addDataName("data").addData(list).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonOut.addResult(Const.ApiResult.EXCEPTION);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0006"));
            jsonOut.addException(ex.getMessage());
            return jsonOut.build();
        }
    }
}
