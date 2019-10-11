package com.rsd.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.rsd.domain.BnzProductQuaModel;
import com.rsd.domain.BnzQuaExtItem;

import java.util.List;

/**
 * @author admin
 * @ClassName hdx
 * @Description 组装vue动态表格数据
 * @Date 2019/5/22 14:32
 * @Version 1.0
 **/
public class VueTable {
    public static JSONArray warpVueTableLable(List<BnzQuaExtItem> bnzQuaExtItemList) throws Exception {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        for (BnzQuaExtItem bnzQuaExtItem : bnzQuaExtItemList
        ) {
            jsonObject = new JSONObject();
            jsonObject.put("label", bnzQuaExtItem.getExtName());
            jsonObject.put("prop", bnzQuaExtItem.getExtCode());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public static JSONArray wrapVueTableData(Page<List<BnzProductQuaModel>> bnzProductQuaModelList) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (Object object : bnzProductQuaModelList
        ) {
            Class aClass = object.getClass();
            BnzProductQuaModel a=(BnzProductQuaModel)object;
            Long id=a.getId();
            Object qua=JSONObject.parse(a.getQuaValue());
            JSONObject J=(JSONObject)qua;
            J.put("id",a.getId());
            J.put("quaCode",a.getQuaCode());
            J.put("isShow",a.getIsShow());
            J.put("orgName",a.getOrgName());
            jsonArray.add(J);
        }
        return jsonArray;
    }
}
