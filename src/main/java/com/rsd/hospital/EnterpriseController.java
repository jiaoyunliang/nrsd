package com.rsd.hospital;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzCollections;
import com.rsd.domain.BnzFileRelation;
import com.rsd.domain.BnzOrgDetailModel;
import com.rsd.domain.RsdOrgInfo;
import com.rsd.service.BnzCollectionsService;
import com.rsd.service.BnzFileRelationService;
import com.rsd.service.BnzOrgDetailService;
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
 * @data 2019-06-06
 * @modifyUser
 * @modifyDate
 */
@Controller
@RequestMapping("/hospital/enterprise")
@PropertySource(value = {"classpath:upload.properties"}, encoding = "utf-8")
public class EnterpriseController {


    private static final Logger logger = LoggerFactory.getLogger(EnterpriseController.class);

    @Autowired
    private MessageManager messageManager;

    @Value("${ueditorFileUrlPerfix}")
    private String fileUrlPrefix;

    @Autowired
    private BnzOrgDetailService bnzOrgDetailService;

    @Autowired
    private BnzFileRelationService bnzFileRelationService;

    @Autowired
    private BnzCollectionsService bnzCollectionsService;

    @GetMapping(value = "/index")
    public String index() {

        return "/hospital/enterprise/index";
    }

    @ResponseBody
    @PostMapping(value = "/list")
    public HashMap list(@RequestBody BnzOrgDetailModel param) {

        try {
            Page page = bnzOrgDetailService.queryOrgDetailPage(param);

            List<BnzOrgDetailModel> list = page.getResult();

            if (!list.isEmpty()) {
                for (BnzOrgDetailModel od : list) {
                    // logo
                    List<BnzFileRelation> logoList = bnzFileRelationService.searchFileByCategoryId(3, od.getOrgId());
                    if (!logoList.isEmpty()) {
                        od.setOrgPic(logoList.get(0));
                    }

                    // brandPics
                    List<BnzFileRelation> brandPics = bnzFileRelationService.searchFileByCategoryId(4, od.getOrgId());
                    if (!brandPics.isEmpty()) {
                        od.setBrandLogo(brandPics);
                    }
                }
            }

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("data").addData(page.getResult())
                    .addData("fileServer", fileUrlPrefix)
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();

        } catch (Exception e) {
            logger.error("EnterpriseController->list", e.getMessage());
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addMessage(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    @GetMapping(value = "/detail")
    public String detail(@RequestParam(value = "id") Long id, Map<String, Object> model) {

        try {
            RsdOrgInfo orgInfo = HttpSessionManager.get(Const.SESSION_ACCOUNT_ORG, RsdOrgInfo.class);

            BnzOrgDetailModel param = new BnzOrgDetailModel();
            param.setOrgId(id);
            BnzOrgDetailModel detail = bnzOrgDetailService.queryOrgDetailById(param);

            BnzCollections collection = new BnzCollections();
            collection.setCategory(3);
            collection.setOrgId(orgInfo.getId());
            collection.setCategoryId(id);
            BnzCollections bnzCollections = bnzCollectionsService.queryCollection(collection);

            if (detail != null && bnzCollections != null) {
                detail.setBnzCollections(bnzCollections);
            }

            model.put("detail", detail);
            model.put("fileServer", fileUrlPrefix);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "/hospital/enterprise/detail";
    }
}
