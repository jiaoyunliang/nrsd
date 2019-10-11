package com.rsd.hospital;

import com.github.pagehelper.Page;
import com.rsd.domain.RsdAccount;
import com.rsd.domain.BnzCollections;
import com.rsd.domain.BnzCollectionsModel;
import com.rsd.service.BnzCollectionsService;
import com.rsd.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tony
 * @data 2019-06-13
 * @modifyUser
 * @modifyDate
 */
@Controller
@RequestMapping("/hospital/collection")
@PropertySource(value = {"classpath:upload.properties"}, encoding = "utf-8")
public class CollectionsController {


    private static final Logger logger = LoggerFactory.getLogger(CollectionsController.class);

    @Value("${ueditorFileUrlPerfix}")
    private String fileUrlPrefix;

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BnzCollectionsService bnzCollectionsService;

    /**
     * @param category    类别（1.关注产品;2.使用中产品;3.企业）
     * @param searchParam orgName productName
     * @param model
     * @return
     */
    @GetMapping("index")
    public String index(
            @RequestParam(value = "category", defaultValue = "1") Integer category,
            @RequestParam(value = "searchParam", required = false) String searchParam,
            Map<String, Object> model) {

        model.put("category", category);
        model.put("searchParam", searchParam);

        return "/hospital/collection/index";
    }


    @ResponseBody
    @PostMapping(value = "addCollection")
    public HashMap addCollection(@RequestBody BnzCollections collection) {

        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);

            if (account != null) {
                collection.setOrgId(account.getOrgId());
                collection.setCreateUser(account.getUserName());
                bnzCollectionsService.saveCollection(collection);
                return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
            } else {
                return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addException(messageManager.getMessage("ERROR.0006")).build();
            }
        } catch (Exception e) {
            logger.error("addCollection", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }


    @ResponseBody
    @PostMapping(value = "removeCollection")
    public HashMap removeCollection(@RequestBody BnzCollections collection) {

        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            if (account != null) {
                collection.setOrgId(account.getOrgId());
                bnzCollectionsService.delCollection(collection);
                return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
            } else {
                return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addException(messageManager.getMessage("ERROR.0006")).build();
            }
        } catch (Exception e) {
            logger.error("removeCollection", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @ResponseBody
    @PostMapping(value = "/list")
    public HashMap list(@RequestBody BnzCollectionsModel model) {

        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);

            if (account != null) {
                model.setOrgId(account.getOrgId());
                //关注  企业
                if (model.getCategory() == 3) {
                    Page page = bnzCollectionsService.queryCollectionOrgPage(model);

                    return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                            .addDataName("data").addData(page.getResult())
                            .addData("fileServer", fileUrlPrefix)
                            .addData("pageInfo", new PageInfoWrap(page).get()).build();

                } else {
                    Page page = bnzCollectionsService.queryCollectionProductPage(model);

                    return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                            .addDataName("data").addData(page.getResult())
                            .addData("fileServer", fileUrlPrefix)
                            .addData("pageInfo", new PageInfoWrap(page).get()).build();
                }
            } else {
                return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addException(messageManager.getMessage("ERROR.0006")).build();
            }
        } catch (Exception e) {
            logger.error("list", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }
}
