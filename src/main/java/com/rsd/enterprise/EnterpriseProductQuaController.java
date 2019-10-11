package com.rsd.enterprise;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.rsd.domain.*;
import com.rsd.service.BnzPackageRecordService;
import com.rsd.service.BnzProductQuaService;
import com.rsd.service.BnzQuaExtItemService;
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
 * @Description 产品资质控制类
 * @Date 2019/6/27 13:28
 * @Version 1.0
 **/
@PropertySource(value = {"classpath:upload.properties"}, encoding = "utf-8")
@Controller
@RequestMapping("/enterprise")
public class EnterpriseProductQuaController {
    private static final Logger logger = LoggerFactory.getLogger(IndexEnterpriseController.class);
    @Value("${ueditorFileUrlPerfix}")
    private String fileUrlPrefix;
    @Value("${global_static_file_location}")
    private String filePath;
    @Value("${file_dir}")
    private String fileDir;
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private BnzProductQuaService productQuaService;
    @Autowired
    private BnzQuaExtItemService bnzQuaExtItemService;
    @Autowired
    private BnzPackageRecordService bnzPackageRecordService;

    /**
     * @author hdx
     * @Description 跳转企业端产品资质列表页面
     * @Date 2019/6/12 10:01
     * @Param [model]
     * @Return java.lang.String
     */
    @RequestMapping(value = "/qua/productlist")
    public String productlist(Map<String, Object> model) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        return "/enterprise/qua/productlist";
    }

    /**
     * @author hdx
     * @Description 跳转到企业产品资质详细页面
     * @Date 2019/7/1 11:25
     * @Param [model, quaName, quaCode]
     * @Return java.lang.String
     */
    @RequestMapping(value = "/qua/productquadetail")
    public String productquadetail(Map<String, Object> model, @RequestParam(value = "quaCode") String quaCode, @RequestParam(value = "quaName") String quaName) {
        RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
        model.put("time", new Date());
        model.put("account", account);
        model.put("quaName", quaName);
        model.put("serverUrl", fileUrlPrefix);//图片服务器地址
        model.put("quaCode", quaCode);//企业资质类型（字典：如注册证等）
        return "/enterprise/qua/productquadetail";
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
            bnzProductQuaModel.setTypeFlag(1);//设置查询条件为企业维护
            bnzProductQuaModel.setQuaType(2);//设置资质为产品资质
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

    @RequestMapping("/qua/productquadetailinfo")
    @ResponseBody
    public HashMap<String, ?> productquadetailinfo(@RequestBody BnzProductQuaModel bnzProductQuaModel) {
        JsonOut<HashMap> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            bnzProductQuaModel.setTypeFlag(1);//设置查询条件为企业维护数据
            bnzProductQuaModel.setQuaType(2);//设置查询条件为查询产品资质数据
            bnzProductQuaModel.setOrgInfoId(account.getOrgId());
            Page<List<BnzProductQuaModel>> bnzProductQuaDetailList = productQuaService.searchBnzProductQuaDetailList(bnzProductQuaModel);
            BnzQuaExtItem bnzQuaExtItem = new BnzQuaExtItem();
            bnzQuaExtItem.setQuaType(bnzProductQuaModel.getQuaType());
            bnzQuaExtItem.setQuaCode(bnzProductQuaModel.getQuaCode());
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            List<BnzQuaExtItem> bnzQuaExtItemList = bnzQuaExtItemService.findQuaExtItemListByQuaType(bnzQuaExtItem);
            JSONArray tableLabel = VueTable.warpVueTableLable(bnzQuaExtItemList);
            if (bnzProductQuaDetailList != null) {
                JSONArray tableData = VueTable.wrapVueTableData(bnzProductQuaDetailList);
                hashMap.put("tableData", tableData);
            } else {
                hashMap.put("tableData", "");
            }
            hashMap.put("tableLabel", tableLabel);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0005"));
            jsonOut.addDataName("page");
            jsonOut.addData(hashMap).addData("pageInfo", new PageInfoWrap(bnzProductQuaDetailList).get());
            return jsonOut.build();
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonOut.addResult(Const.ApiResult.EXCEPTION);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0006"));
            jsonOut.addException(ex.getMessage());
            return jsonOut.build();
        }
    }

    @RequestMapping(value = "/qua/dynamicCreateForm")
    @ResponseBody
    public HashMap<String, ?> dynamicCreateForm(@RequestBody BnzProductQua bnzProductQua) {
        JsonOut<HashMap> jsonOut = new JsonOut();
        BnzQuaExtItem bnzQuaExtItem = new BnzQuaExtItem();
        try {
            bnzQuaExtItem.setQuaType(2);//查询产品资质表单字段
            bnzQuaExtItem.setQuaCode(bnzProductQua.getQuaCode());
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            List<BnzQuaExtItem> bnzQuaExtItemList = bnzQuaExtItemService.findQuaExtItemListByQuaType(bnzQuaExtItem);
            JSONArray formItem = VueForm.warpVueFormLable(bnzQuaExtItemList);
            hashMap.put("formItem", formItem);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0005"));
            jsonOut.addDataName("formItem");
            jsonOut.addData(hashMap);
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
     * @Description 插入企业端产品资质
     * @Date 2019/6/12 17:02
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping(value = "/qua/insertproducequadetailinfo")
    @ResponseBody
    public HashMap<String, ?> insertproducequadetailinfo(@RequestBody BnzProductQuaModel bnzProductQuaModel) {
        JsonOut<List<BnzProductQuaModel>> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            RsdOrgInfo rsdOrgInfo = HttpSessionManager.get(Const.SESSION_ACCOUNT_ORG, RsdOrgInfo.class);
            bnzProductQuaModel.setCreateUser(account.getUserName());
            bnzProductQuaModel.setUpdateUser(account.getUserName());
            bnzProductQuaModel.setIsDel(0);
            bnzProductQuaModel.setIsShow(0);
            bnzProductQuaModel.setTypeFlag(1);//设置为企业维护数据
            bnzProductQuaModel.setOrgName(rsdOrgInfo.getOrgName());//企业端默认存当前登陆所属机构
            bnzProductQuaModel.setQuaType(2);//设置维护产品资质类型为产品资质
            bnzProductQuaModel.setOrgInfoId(account.getOrgId());
            productQuaService.insertBnzProductQuaDetail(bnzProductQuaModel);
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
     * @Description 更新产品资质
     * @Date 2019/6/12 17:02
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping(value = "/qua/upadteproducequadetailinfo")
    @ResponseBody
    public HashMap<String, ?> upadteproducequadetailinfo(@RequestBody BnzProductQuaModel bnzProductQuaModel) {
        JsonOut<List<BnzProductQuaModel>> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            bnzProductQuaModel.setUpdateUser(account.getUserName());
            productQuaService.updateBnzProductQuaDetail(bnzProductQuaModel);
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
     * @Description 删除医院端维护企业资质数据
     * @Date 2019/6/12 17:02
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping(value = "/qua/deleteproductquadetailinfo")
    @ResponseBody
    public HashMap<String, ?> deleteproductquadetailinfo(@RequestBody BnzProductQuaModel bnzProductQuaModel) {
        JsonOut<List<BnzProductQuaModel>> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            bnzProductQuaModel.setUpdateUser(account.getUserName());
            bnzProductQuaModel.setIsDel(new Integer(1));
            productQuaService.deleteBnzProductQuaDetailById(bnzProductQuaModel);
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


    @RequestMapping(value = "/qua/productDoPackage")
    @ResponseBody
    public HashMap<String, ?> productDoPackage(@RequestBody List<BnzPackageRecordModel> bnzPackageRecordModelList) {
        JsonOut<List<BnzInstitutionQuaModel>> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            BnzProductQuaModel bnzInstitutionQuaModel = new BnzProductQuaModel();
            bnzInstitutionQuaModel.setOrgInfoId(account.getOrgId());
            bnzInstitutionQuaModel.setTypeFlag(1);//企业维护
            List<BnzProductQuaModel> list = productQuaService.searchPackRecrodProductList(bnzInstitutionQuaModel);
            List<ElementBean> beans = new ArrayList<ElementBean>();
            ObjectMapper mapper = new ObjectMapper();
            if (list != null) {
                for (BnzProductQuaModel bnzProductQuaModel : list
                ) {
                    String qv = bnzProductQuaModel.getQuaValue();
                    if (StringUtils.isNotEmpty(qv)) {
                        Map<String, String> tmpMap = mapper.readValue(qv, Map.class);
                        if (StringUtils.isNotEmpty(tmpMap.get("imgstr"))) {
                            String tmp[] = tmpMap.get("imgstr").split(",");
                            for (String p : tmp) {
                                ElementBean e1 = new ElementBean();
                                e1.setContent(filePath + p);
                                e1.setType(ElementBean.TYPE_IMAGE);
                                beans.add(e1);
                            }
                        }
                    }
                }
                if (!beans.isEmpty()) {
                    BnzPackageRecordModel bnzPackageRecord = new BnzPackageRecordModel();
                    Date createDate = new Date();
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String pdf, zip;
                    bnzPackageRecord.setType(2);//设置打包记录类型为产品资质
                    pdf = fileDir + "qyzz" + sf.format(createDate) + ".pdf";
                    zip = fileDir + "qyzz" + sf.format(createDate) + ".zip";
                    PdfCreator.createPdf(beans, filePath + pdf);
                    ZipUtils.zipSingleFile(filePath + pdf, filePath + zip);
                    bnzPackageRecord.setCreateUser(account.getUserName());
                    bnzPackageRecord.setOrgInfoId(account.getOrgId());
                    bnzPackageRecord.setPackageUrl(zip);
                    bnzPackageRecordService.insert(bnzPackageRecord);
                    jsonOut.addMessage(messageManager.getMessage("ERROR.0005")).addDataName("url").addData(fileUrlPrefix + bnzPackageRecord.getPackageUrl());
                }
            }
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
    @RequestMapping(value = "/qua/productPackageList")
    @ResponseBody
    public HashMap<String, ?> productPackageList(@RequestBody BnzPackageRecordModel bnzPackageRecordModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            bnzPackageRecordModel.setOrgInfoId(account.getOrgId());
            bnzPackageRecordModel.setType(2);//产品资质
            Page<List<BnzPackageRecordModel>> page = bnzPackageRecordService.findPackageRecordPage(bnzPackageRecordModel);
            if (page != null) {
                List list = page.getResult();
                for (Object object : list
                ) {
                    BnzPackageRecord bnzPackageRecord = (BnzPackageRecord) object;
                    String packageUrl = bnzPackageRecord.getPackageUrl();
                    bnzPackageRecord.setPackageUrl(fileUrlPrefix + packageUrl);
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
    @RequestMapping(value = "/qua/productPackageListState")
    @ResponseBody
    public HashMap<String, ?> productPackageListState(@RequestBody BnzPackageRecordModel bnzPackageRecordModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            List<BnzPackageRecordModel> list = bnzPackageRecordService.selectProductQuaQuaPackState(bnzPackageRecordModel);
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
