package com.rsd.hospital;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.rsd.domain.RsdAccount;
import com.rsd.domain.BnzProductQua;
import com.rsd.domain.BnzProductQuaModel;
import com.rsd.domain.BnzQuaExtItem;
import com.rsd.service.BnzProductQuaService;
import com.rsd.service.BnzQuaExtItemService;
import com.rsd.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author admin
 * @ClassName hdx
 * @Description 产品资质
 * @Date 2019/6/19 10:57
 * @Version 1.0
 **/
@RestController
@RequestMapping("/hospital")
public class ProductQuaController {
    private static final Logger logger = LoggerFactory.getLogger(ProductQuaController.class);
    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private BnzProductQuaService bnzProductQuaService;

    @Autowired
    private BnzQuaExtItemService bnzQuaExtItemService;
    @Value("${ueditorFileUrlPerfix}")
    private String fileUrlPrefix;

    @RequestMapping("/qua/productquadetailinfo")
    @ResponseBody
    public HashMap<String, ?> productquadetailinfo(@RequestBody BnzProductQuaModel bnzProductQuaModel) {
        JsonOut<HashMap> jsonOut = new JsonOut();
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            bnzProductQuaModel.setOrgInfoId(account.getOrgId());
            Page<List<BnzProductQuaModel>> bnzProductQuaDetailList = bnzProductQuaService.searchBnzProductQuaDetailList(bnzProductQuaModel);
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
    /**
     * @author hdx
     * @Description 全网资质详细查看
     * @Date 2019/6/25 15:18
     * @Param [bnzProductQuaModel]
     * @Return java.util.HashMap<java.lang.String,?>
     */
    @RequestMapping("/qua/allproductquadetailinfo")
    @ResponseBody
    public HashMap<String, ?> allproductquadetailinfo(@RequestBody BnzProductQuaModel bnzProductQuaModel) {
        JsonOut<HashMap> jsonOut = new JsonOut();
        try {
            Page<List<BnzProductQuaModel>> bnzProductQuaDetailList = bnzProductQuaService.searchBnzProductQuaDetailList(bnzProductQuaModel);
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
            bnzProductQuaService.deleteBnzProductQuaDetailById(bnzProductQuaModel);
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

    @RequestMapping(value = "/qua/dynamicCreateForm")
    @ResponseBody
    public HashMap<String, ?> dynamicCreateForm(@RequestBody BnzProductQua bnzProductQua) {
        JsonOut<HashMap> jsonOut = new JsonOut();
        BnzQuaExtItem bnzQuaExtItem = new BnzQuaExtItem();
        try {
            bnzQuaExtItem.setQuaType(2);//产品资质
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
     * @Description 更新医院端维护产品资质数据
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
            bnzProductQuaService.updateBnzProductQuaDetail(bnzProductQuaModel);
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
     * @Description 插入医院端维护产品资质数据
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
            bnzProductQuaModel.setCreateUser(account.getUserName());
            bnzProductQuaModel.setIsDel(0);
            bnzProductQuaModel.setIsShow(0);
            bnzProductQuaModel.setUpdateUser(account.getUserName());
            bnzProductQuaModel.setTypeFlag(2);//设置维护机构为医院
            bnzProductQuaModel.setQuaType(2);//设置维护资质类型为产品资质
            bnzProductQuaModel.setOrgInfoId(account.getOrgId());
            bnzProductQuaService.insertBnzProductQuaDetail(bnzProductQuaModel);
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
     * @Description 全网资质瀑布产品资质查询
     * @Date 2019/6/24 16:17
     * @Param [bnzProductQuaModel]
     * @Return java.util.HashMap
     */
    @ResponseBody
    @RequestMapping(value = "/qua/product/list")
    public HashMap list(@RequestBody BnzProductQuaModel bnzProductQuaModel) {
        try {
            bnzProductQuaModel.setQuaType(2);//设置查询产品资质类型为产品资质
            bnzProductQuaModel.setTypeFlag(1);//设置维护机构为企业
            bnzProductQuaModel.setIsShow(1);//设置查询条件为医院可见
            Page page = bnzProductQuaService.searchBnzProductQuaDetailList(bnzProductQuaModel);

            List<BnzProductQuaModel> list = page.getResult();

            if (!list.isEmpty()) {
                for (BnzProductQuaModel od : list) {
                    // logo
                    String quaValue = od.getQuaValue();
                    JSONObject jsonObject = JSONObject.parseObject(quaValue);
                    String imgStr = (String) jsonObject.get("imgstr");
                    String quaName = (String) jsonObject.get("quaName");
                    od.setQuaName(quaName);
                    String img[] = imgStr.split(",");
                    if (img.length > 0) {
                        od.setQuaPic(img[0]);
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
