package com.rsd.api;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.rsd.domain.BnzProductQuaModel;
import com.rsd.domain.BnzQuaExtItem;
import com.rsd.service.BnzProductQuaService;
import com.rsd.service.BnzQuaExtItemService;
import com.rsd.utils.*;
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
 * @Description 产品资质
 * @Date 2019/5/13 15:52
 * @Version 1.0
 **/
@RestController
@RequestMapping("/productqua")
public class BnzProductQuaApi {
    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private BnzProductQuaService bnzProductQuaService;

    @Autowired
    private BnzQuaExtItemService bnzQuaExtItemService;

    @RequestMapping("productqualist")
    @ResponseBody
    public HashMap<String, ?> productqualist(@RequestBody BnzProductQuaModel bnzProductQuaModel) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            Page<List<BnzProductQuaModel>> page = bnzProductQuaService.searchBnzProductQuaList(bnzProductQuaModel);
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

    @RequestMapping("productquadetail")
    @ResponseBody
    public HashMap<String, ?> productquadetail(@RequestBody BnzProductQuaModel bnzProductQuaModel) {
        JsonOut<HashMap> jsonOut = new JsonOut();
        try {
            Page<List<BnzProductQuaModel>> bnzProductQuaDetailList = bnzProductQuaService.searchBnzProductQuaDetailList(bnzProductQuaModel);
            BnzQuaExtItem bnzQuaExtItem=new BnzQuaExtItem();
            bnzQuaExtItem.setQuaType(bnzProductQuaModel.getQuaType());
            bnzQuaExtItem.setQuaCode(bnzProductQuaModel.getQuaCode());
            HashMap<String,Object> hashMap=new HashMap<String,Object>();
            List<BnzQuaExtItem> bnzQuaExtItemList=bnzQuaExtItemService.findQuaExtItemListByQuaType(bnzQuaExtItem);
            JSONArray tableLabel=VueTable.warpVueTableLable(bnzQuaExtItemList);
            if(bnzProductQuaDetailList!=null) {
                JSONArray tableData = VueTable.wrapVueTableData(bnzProductQuaDetailList);
                hashMap.put("tableData",tableData);
            }else{
                hashMap.put("tableData","");
            }


            hashMap.put("tableLabel",tableLabel);
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
}
