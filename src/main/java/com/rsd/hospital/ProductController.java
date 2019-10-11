package com.rsd.hospital;

import com.github.pagehelper.Page;
import com.rsd.domain.RsdAccount;
import com.rsd.domain.BnzProductModel;
import com.rsd.domain.BnzProductTypeModel;
import com.rsd.service.BnzProductService;
import com.rsd.service.BnzProductTypeService;
import com.rsd.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @data 2019-06-05
 * @modifyUser
 * @modifyDate
 */
@Controller
@RequestMapping("/hospital/product")
@PropertySource(value = {"classpath:upload.properties"}, encoding = "utf-8")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BnzProductService bnzProductService;


    @Autowired
    private BnzProductTypeService bnzProductTypeService;

    @Value("${ueditorFileUrlPerfix}")
    private String fileUrlPrefix;


    @GetMapping(value = "list")
    public String list(
            @RequestParam(value = "productTypeId", required = false) Long productTypeId,
            @RequestParam(value = "productName", required = false) String productName,
            Map<String, Object> model) {

        try {
            model.put("productTypeId", productTypeId);
            model.put("productName", productName);

            if(productTypeId!=null){
                bnzProductTypeService.updateProductTypeClickNum(productTypeId);
            }

            //产品类别
            List<BnzProductTypeModel> productTypeModelList = bnzProductTypeService.queryProductType();

            model.put("productTypeList", productTypeModelList);
            model.put("fileServer", fileUrlPrefix);
        } catch (Exception e) {
            logger.error("list", e);
        }

        return "/hospital/product/list";
    }

    /**
     * 医院端 首页
     *
     * @param param productTypeId
     *              isHospitalView
     *              isHot
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/queryProductForIndex")
    public HashMap queryProductForIndex(@RequestBody BnzProductModel param) {

        logger.info(param.toString());

        try {
            Page page = bnzProductService.selectProductListPage(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("data").addData(page.getResult())
                    .addData("fileServer", fileUrlPrefix).build();
        } catch (Exception e) {
            logger.error("queryProductForIndex", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    /**
     * 医院端 产品列表
     *
     * @param param productTypeId, productName
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/queryProductList")
    public HashMap queryProductList(@RequestBody BnzProductModel param) {

        logger.info(param.toString());

        try {
            Page page = bnzProductService.selectProductPage(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page")
                    .addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get())
                    .addData("fileServer", fileUrlPrefix).build();
        } catch (Exception e) {
            logger.error("queryProductList", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }


    @GetMapping(value = "view")
    public String list(
            @RequestParam(value = "id") Long id,
            Map<String, Object> model) {
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            BnzProductModel param = new BnzProductModel();
            param.setId(id);
            if (account != null) {
                param.setOrgId(account.getOrgId());
            }
            //产品
            BnzProductModel productModel = bnzProductService.selectProduct(param);
            model.put("product", productModel);
            model.put("fileServer", fileUrlPrefix);
        } catch (Exception e) {
            logger.error("view", e);
        }

        return "/hospital/product/detail";
    }


}
