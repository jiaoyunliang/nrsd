package com.rsd.enterprise;

import com.rsd.domain.RsdDictInfo;
import com.rsd.service.BnzDictInfoService;
import com.rsd.utils.Const;
import com.rsd.utils.JsonOut;
import com.rsd.utils.MessageManager;
import com.rsd.utils.SessionManager;
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
 * @Description 字典表业务
 * @Date 2019/6/18 14:49
 * @Version 1.0
 **/
@RestController
@RequestMapping("/enterprise")
public class EnterpriseDictInfoController {
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseDictInfoController.class);
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private BnzDictInfoService bnzDictInfoService;
    @Autowired
    private SessionManager sessionManager;

    /**
     * @author hdx
     * @Description 查询系统字典
     * @Date 2019/6/11 16:34
     * @Param [bnzInstitutionQuaModel]
     * @Return java.util.HashMap<java.lang.String, ?>
     */
    @RequestMapping("/search/dictInfo")
    @ResponseBody
    public HashMap<String, ?> hospitalquainfo(@RequestBody RsdDictInfo rsdDictInfo) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            List<RsdDictInfo> list = bnzDictInfoService.queryDictByCategoryId(rsdDictInfo);
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
