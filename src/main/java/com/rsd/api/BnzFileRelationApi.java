package com.rsd.api;

import com.rsd.domain.BnzFileRelation;
import com.rsd.domain.RsdAccountModel;
import com.rsd.service.BnzFileRelationService;
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

/**
 * @author admin
 * @ClassName hdx
 * @Description 文件处理
 * @Date 2019/5/7 16:22
 * @Version 1.0
 **/
@RestController
@RequestMapping("/bnzfilerelation")
public class BnzFileRelationApi {
    private static final Logger logger = LoggerFactory.getLogger(BnzFileRelationApi.class);
    @Autowired
    private BnzFileRelationService bnzFileRelationService;
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private SessionManager sessionManager;

    @RequestMapping("/add")
    @ResponseBody
    public HashMap<String, ?> addFileRelation(@RequestBody BnzFileRelation bnzFileRelation) {
        JsonOut<String> jsonOut = new JsonOut();
        try {
            RsdAccountModel account = sessionManager.getCurrentUser();
            bnzFileRelation.setCreateUser(account.getUserName());
            int flag = bnzFileRelationService.save(bnzFileRelation);
            if (flag == 1) {
                jsonOut.addMessage(messageManager.getMessage("ERROR.0005"));
            } else {
                jsonOut.addMessage(messageManager.getMessage("ERROR.0006"));
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
}
