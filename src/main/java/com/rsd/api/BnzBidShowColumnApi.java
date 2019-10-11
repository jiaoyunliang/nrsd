package com.rsd.api;

import com.rsd.domain.BnzBidShowColumn;
import com.rsd.service.BnzBidShowColumnService;
import com.rsd.utils.Const;
import com.rsd.utils.JsonOut;
import com.rsd.utils.MessageManager;
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
 * @author jiaoyl
 * @Description 项目控制类
 * @Date 15:51 2019/6/27
 */
@RestController
@RequestMapping("/bid/show/column")
public class BnzBidShowColumnApi {
    private static final Logger logger = LoggerFactory.getLogger(BnzBidShowColumnApi.class);
    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BnzBidShowColumnService bnzBidShowColumnService;

    @RequestMapping("/list")
    @ResponseBody
    public HashMap<String, ?> searchBidShowColumns(@RequestBody Long projectId) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            List<BnzBidShowColumn> bnzBidShowColumns = this.bnzBidShowColumnService.queryBidShowColumn(projectId);
            jsonOut.addDataName("data").addData(bnzBidShowColumns);
            return jsonOut.build();
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            jsonOut.addResult(Const.ApiResult.EXCEPTION);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0006"));
            jsonOut.addException(ex.getMessage());
            return jsonOut.build();
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public HashMap<String, ?> updateBidShowColumns(@RequestBody List<BnzBidShowColumn> bnzBidShowColumns) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            this.bnzBidShowColumnService.updateBidShow(bnzBidShowColumns);
            return jsonOut.build();
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            jsonOut.addResult(Const.ApiResult.EXCEPTION);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0006"));
            jsonOut.addException(ex.getMessage());
            return jsonOut.build();
        }
    }
}
