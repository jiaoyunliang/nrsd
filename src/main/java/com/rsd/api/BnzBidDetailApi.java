package com.rsd.api;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.github.pagehelper.Page;
import com.rsd.domain.BnzBidDetailModel;
import com.rsd.excel.ExcelListener;
import com.rsd.service.BnzBidDetailService;
import com.rsd.service.BnzBidShowColumnService;
import com.rsd.utils.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @author jiaoyl
 * @Description 中拓数据列表
 * @Date 15:51 2019/6/27
 */
@RestController
@RequestMapping("/bid/detail")
public class BnzBidDetailApi {
    private static final Logger logger = LoggerFactory.getLogger(BnzBidDetailApi.class);
    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BnzBidShowColumnService bnzBidShowColumnService;

    @Autowired
    private BnzBidDetailService bnzBidDetailService;

    @Autowired
    private SessionManager sessionManager;

    @RequestMapping("/process")
    @ResponseBody
    public HashMap<String, ?> processBidDetail(@RequestBody BnzBidDetailModel bidDetailModel) throws Exception{
        JsonOut<List> jsonOut = new JsonOut();
        FileInputStream inputStream = null;

        try {
            ConfigurationManager cm = ConfigurationManager.getManager("upload");

            bidDetailModel.setCreateUser(sessionManager.getCurrentUser().getUserName());

            ExcelListener excelListener = new ExcelListener();
            excelListener.setBnzBidDetail(bidDetailModel);
            excelListener.setBnzBidDetailService(this.bnzBidDetailService);

            String path = cm.getProperty("global_static_file_location")+ cm.getProperty("attach_dir");

            inputStream = new FileInputStream(path + bidDetailModel.getFileName());

            EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1), excelListener);


            jsonOut.addMessage(messageManager.getMessage("ERROR.0005"));
            return jsonOut.build();
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            jsonOut.addResult(Const.ApiResult.EXCEPTION);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0006"));
            jsonOut.addException(ex.getMessage());
            return jsonOut.build();
        }finally {
            inputStream.close();
        }
    }


    @RequestMapping("/delete")
    @ResponseBody
    public HashMap<String, ?> deleteBidDetail(@RequestBody BnzBidDetailModel bidDetailModel) throws Exception{
        JsonOut<List> jsonOut = new JsonOut();
        try {

            this.bnzBidDetailService.deleteBidDetail(bidDetailModel.getProjectId());
            jsonOut.addMessage(messageManager.getMessage("ERROR.0005"));
            return jsonOut.build();
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            jsonOut.addResult(Const.ApiResult.EXCEPTION);
            jsonOut.addMessage(messageManager.getMessage("ERROR.0006"));
            jsonOut.addException(ex.getMessage());
            return jsonOut.build();
        }
    }

    @ApiOperation(value = "queryBidDetailPage", notes = "queryBidDetailPage")
    @PostMapping(value = "/queryBidDetailPage")
    public HashMap queryNewsPage(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody BnzBidDetailModel param) {
        try {
            Page<List> page = this.bnzBidDetailService.queryBidDetail(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page")
                    .addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }
}
