package com.rsd.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rsd.domain.BnzQuaExtItem;

import java.util.List;

/**
 * @author admin
 * @ClassName hdx
 * @Description Vue表单
 * @Date 2019/6/20 10:10
 * @Version 1.0
 **/
public class VueForm {
    public static JSONArray warpVueFormLable(List<BnzQuaExtItem> bnzQuaExtItemList) throws Exception {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        for (BnzQuaExtItem bnzQuaExtItem : bnzQuaExtItemList) {
            jsonObject = new JSONObject();
            Integer extType = bnzQuaExtItem.getExtType();
            if (extType == 1) {
                jsonObject.put("type", "text");
            } else if (extType == 2) {
                jsonObject.put("type", "date");
            }
            jsonObject.put("label", bnzQuaExtItem.getExtName());
            jsonObject.put("prop", bnzQuaExtItem.getExtCode());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
