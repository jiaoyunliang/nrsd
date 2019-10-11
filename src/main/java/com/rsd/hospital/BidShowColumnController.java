package com.rsd.hospital;

import com.rsd.api.BnzBidShowColumnApi;
import com.rsd.domain.BnzBidShowColumn;
import com.rsd.service.BnzBidShowColumnService;
import com.rsd.utils.Const;
import com.rsd.utils.JsonOut;
import com.rsd.utils.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author admin
 * @ClassName hdx
 * @Description TODO
 * @Date 2019/7/5 16:52
 * @Version 1.0
 **/
@Controller
@RequestMapping("/hospital")
public class BidShowColumnController {
    private static final Logger logger = LoggerFactory.getLogger(BnzBidShowColumnApi.class);
    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BnzBidShowColumnService bnzBidShowColumnService;

    @RequestMapping("/bid/showcolumn")
    @ResponseBody
    public HashMap<String, ?> searchBidShowColumns(@RequestBody BnzBidShowColumn bnzBidShowColumn) {
        JsonOut<List> jsonOut = new JsonOut();
        try {
            List<BnzBidShowColumn> bnzBidShowColumns = this.bnzBidShowColumnService.queryBidShowColumn(bnzBidShowColumn.getProjectId());
            jsonOut.addDataName("data").addData(bnzBidShowColumns);
            return jsonOut.build();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            jsonOut.addResult(Const.ApiResult.EXCEPTION);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0006"));
            jsonOut.addException(ex.getMessage());
            return jsonOut.build();
        }
    }
}
