package com.rsd.enterprise;

import com.github.pagehelper.Page;
import com.rsd.domain.RsdAccount;
import com.rsd.domain.BnzFileRelation;
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

import java.util.*;

/**
 * @author tony
 * @data 2019-06-24
 * @modifyUser
 * @modifyDate
 */
@Controller
@RequestMapping("/enterprise/product")
@PropertySource(value = {"classpath:upload.properties"}, encoding = "utf-8")
public class ProductEnterpriseController {

    private static final Logger logger = LoggerFactory.getLogger(ProductEnterpriseController.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BnzProductService bnzProductService;

    @Autowired
    private BnzProductTypeService bnzProductTypeService;

    @Value("${ueditorFileUrlPerfix}")
    private String fileUrlPrefix;

    @GetMapping(value = "list")
    public String list(){
        return "/enterprise/product/list";
    }

    @ResponseBody
    @PostMapping(value = "queryProductList")
    public HashMap queryProductList(@RequestBody BnzProductModel param){
        logger.info(param.toString());
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            param.setOrgId(account.getOrgId());
            Page page = bnzProductService.queryProductPage(param);

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
    public String view(
            @RequestParam(value = "id") Long id,
            Map<String, Object> model) {
        try {
            BnzProductModel param = new BnzProductModel();
            param.setId(id);

            //产品
            BnzProductModel productModel = bnzProductService.selectProduct(param);
            model.put("product", productModel);
            model.put("fileServer", fileUrlPrefix);
        } catch (Exception e) {
            logger.error("view", e);
        }
        return "/enterprise/product/detail";
    }

    @GetMapping(value = "add")
    public String add() {
        return "/enterprise/product/add";
    }


    @GetMapping(value = "edit")
    public String edit(
            @RequestParam(value = "id") Long id,
            Map<String, Object> model) {
        try {
            model.put("productId", id);
            model.put("fileServer", fileUrlPrefix);
        } catch (Exception e) {
            logger.error("edit", e);
        }
        return "/enterprise/product/edit";
    }

    @ResponseBody
    @PostMapping(value = "queryProductById")
    public HashMap queryProductById(@RequestBody BnzProductModel param){
        try {
            //产品
            BnzProductModel productModel = bnzProductService.selectProduct(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("data")
                    .addData(productModel).build();
        } catch (Exception e) {
            logger.error("queryProductById", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }


    @ResponseBody
    @PostMapping(value = "queryProductTypeList")
    public HashMap queryProductTypeList(){
        try {

            List<BnzProductTypeModel> list = bnzProductTypeService.queryProductType();

            List<Map<String,Object>> typeList = new ArrayList<>();
            for(BnzProductTypeModel type : list){
                Map<String,Object> map = new HashMap<>();
                map.put("id",type.getId());
                map.put("name",type.getName());
                typeList.add(map);
            }

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("data")
                    .addData(typeList).build();
        } catch (Exception e) {
            logger.error("queryProductTypeList", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }



    @ResponseBody
    @PostMapping(value = "saveProduct")
    public HashMap saveProduct(@RequestBody BnzProductModel param){
        logger.info(param.toString());
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            param.setOrgId(account.getOrgId());
            param.setCreateUser(account.getUserName());

            if (param.getVideo()!=null){
                param.getVideo().setIsDel(0L);
                param.getVideo().setType(2L);
                param.getVideo().setCreateUser(account.getUserName());
            }

            if (!param.getPicList().isEmpty()){
                for (BnzFileRelation pic : param.getPicList()){
                    pic.setType(1L);
                    pic.setIsDel(0L);
                    pic.setCreateUser(account.getUserName());
                }
            }

            bnzProductService.saveProduct(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("saveProduct", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ResponseBody
    @PostMapping(value = "updateProduct")
    public HashMap updateProduct(@RequestBody BnzProductModel param){
        logger.info(param.toString());
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            param.setUpdateTime(new Date());
            param.setUpdateUser(account.getUserName());

            if (param.getVideo()!=null){
                param.getVideo().setIsDel(0L);
                param.getVideo().setType(2L);
                param.getVideo().setCreateUser(account.getUserName());
            }

            if (!param.getPicList().isEmpty()){
                for (BnzFileRelation pic : param.getPicList()){
                    pic.setType(1L);
                    pic.setIsDel(0L);
                    pic.setCreateUser(account.getUserName());
                }
            }

            bnzProductService.updateProduct(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("updateProduct", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

}
