package com.rsd.enterprise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.pagehelper.Page;
import com.rsd.domain.*;
import com.rsd.service.*;
import com.rsd.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author tony
 * @data 2019-05-29
 * @modifyUser
 * @modifyDate
 */
@Controller
@RequestMapping("/enterprise")
@PropertySource(value = {"classpath:upload.properties"},encoding="utf-8")
public class IndexEnterpriseController {



    private static final Logger logger = LoggerFactory.getLogger(IndexEnterpriseController.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BnzNewsService bnzNewsService;

    @Autowired
    private BnzProductService bnzProductService;

    @Autowired
    private BnzAdService bnzAdService;

    @Autowired
    private BnzOrgDetailService bnzOrgDetailService;

    @Autowired
    private BnzAccountApplyService bnzAccountApplyService;

    @Autowired
    private BnzOrgInfoService bnzOrgInfoService;

    @Value("${ueditorFileUrlPerfix}")
    private String fileUrlPrefix;

    @RequestMapping(value = {"","/", "/index"})
    public String index(Map<String, Object> model, HttpSession session) {

        try {

            PageInput pageInput = new PageInput();
            pageInput.setCurrent(1);
            pageInput.setSize(4);

            //新闻 资料下载
            BnzNewsModel newsParam = new BnzNewsModel();
            newsParam.setPageInput(pageInput);
            newsParam.setSysId(Const.E_SYS_ID);
            newsParam.setNewsType(1);
            Page<List> page = bnzNewsService.queryNewsList(newsParam);
            model.put("news1",page.getResult());

            //新闻 行业动态
            newsParam = new BnzNewsModel();
            newsParam.setPageInput(pageInput);
            newsParam.setSysId(Const.E_SYS_ID);
            newsParam.setNewsType(2);
            page = bnzNewsService.queryNewsList(newsParam);
            model.put("news2",page.getResult());

            //新闻 项目公告
            pageInput = new PageInput();
            pageInput.setCurrent(1);
            pageInput.setSize(8);
            newsParam = new BnzNewsModel();
            newsParam.setPageInput(pageInput);
            newsParam.setSysId(Const.E_SYS_ID);
            newsParam.setNewsType(3);
            page = bnzNewsService.queryNewsList(newsParam);
            model.put("news3",page.getResult());

            //ad
            BnzAd adParam = new BnzAd();
            adParam.setSysId(Const.E_SYS_ID);
            adParam.setStatus(0);
            adParam.setType(100);
            List<BnzAd> adList = bnzAdService.queryAdList(adParam);
            Map<String,List> adMap = new HashMap<>();
            for (BnzAd ad : adList){
                if(adMap.containsKey(ad.getType()+"")){
                    adMap.get(ad.getType()+"").add(ad);
                } else {
                    List<BnzAd> list = new ArrayList<>();
                    list.add(ad);
                    adMap.put(ad.getType()+"",list);
                }
            }
            for(Map.Entry<String,List> entry:adMap.entrySet()){
                model.put("adGroup"+entry.getKey(), entry.getValue());
            }

            //推荐产品
            if(session.getAttribute("hotProductList")==null){
                pageInput = new PageInput();
                pageInput.setCurrent(1);
                pageInput.setSize(10);
                BnzProductModel productModel = new BnzProductModel();
                productModel.setIsHot(1);
                productModel.setPageInput(pageInput);
                List hotProductList = bnzProductService.selectProductListPage(productModel).getResult();
                session.setAttribute("hotProductList",hotProductList);
            }

            //排行榜
            if(session.getAttribute("indexEnterpriseList")==null){
                pageInput = new PageInput();
                pageInput.setCurrent(1);
                pageInput.setSize(10);

                //好评 排行榜
                RsdOrgInfoModel orgParam = new RsdOrgInfoModel();
                orgParam.setPageInput(pageInput);
                List indexEnterpriseList = bnzOrgInfoService.queryEnterpriseList(orgParam).getResult();
                session.setAttribute("indexEnterpriseList",indexEnterpriseList);

                //生产企业 排行榜
                orgParam = new RsdOrgInfoModel();
                orgParam.setEnterpriseType(0);
                orgParam.setPageInput(pageInput);
                List indexEnterpriseList0 = bnzOrgInfoService.queryEnterpriseList(orgParam).getResult();
                session.setAttribute("indexEnterpriseList0",indexEnterpriseList0);

                //经销企业 排行榜
                orgParam = new RsdOrgInfoModel();
                orgParam.setEnterpriseType(1);
                orgParam.setPageInput(pageInput);
                List indexEnterpriseList1 = bnzOrgInfoService.queryEnterpriseList(orgParam).getResult();
                session.setAttribute("indexEnterpriseList1",indexEnterpriseList1);
            }


        }catch (Exception e){
            logger.error("index",e);
        }
        model.put("fileServer", fileUrlPrefix);
        return "/enterprise/index";
    }

    @RequestMapping("expired")
    public String expired(HttpServletRequest request, HttpServletResponse response){
        String contentType = request.getHeader("Accept");
        logger.info("Content-Type:" + contentType);

        if (request.getHeader("Accept").indexOf("application/json") > -1) {
            try {
                response.reset();
                response.setContentType("application/json;charset=utf-8");

                Map<String,Object> map = new HashMap<String, Object>();

                map.put("result", Const.ApiResult.TOKEN_PAST_DUE);
                map.put("message",messageManager.getMessage("ERROR.0003"));
                map.put("exceptionMsg",messageManager.getMessage("ERROR.0003"));

                ObjectMapper om = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

                PrintWriter out = response.getWriter();
                out.write(om.writeValueAsString(map));
                out.flush();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return "/enterprise/expired";
        }
    }

    @ResponseBody
    @PostMapping(value = "accountApply")
    public HashMap accountApply(@RequestBody BnzAccountApply param){
        try {
            param.setCreateTime(new Date());

            bnzAccountApplyService.saveAccountApply(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("accountApply", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    /**
     * 企业黄页
     * @param model
     * @return
     */
    @RequestMapping("detail")
    public String detail(Map<String, Object> model){
        model.put("fileServer", fileUrlPrefix);
        return "/enterprise/detail";
    }

    /**
     * 企业黄页
     * @return
     */
    @ResponseBody
    @PostMapping(value = "queryOrgDetail")
    public HashMap queryOrgDetail(){
        try{
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);
            BnzOrgDetailModel param = new BnzOrgDetailModel();
            param.setOrgId(account.getOrgId());

            BnzOrgDetailModel detail = bnzOrgDetailService.queryOrgDetailById(param);
            if(detail!=null){
                return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                        .addDataName("data")
                        .addData(detail).build();
            } else {
                return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                        .addDataName("data")
                        .addData(null).build();
            }
        }catch (Exception e){
            logger.error("queryOrgDetail", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

    /**
     * 企业黄页
     * @return
     */
    @ResponseBody
    @PostMapping(value = "saveOrgDetail")
    public HashMap saveOrgDetail(@RequestBody BnzOrgDetailModel param){
        try{
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);

            if (param.getOrgPic()!=null){
                param.getOrgPic().setType(1L);
                param.getOrgPic().setIsDel(0L);
                param.getOrgPic().setCreateUser(account.getUserName());
            }

            if (!param.getBrandLogo().isEmpty()){
                for (BnzFileRelation logo : param.getBrandLogo()){
                    logo.setType(1L);
                    logo.setIsDel(0L);
                    logo.setCreateUser(account.getUserName());
                }
            }

            if(param.getOrgId()!=null){
                param.setUpdateTime(new Date());
                param.setUpdateUser(account.getUserName());
                bnzOrgDetailService.updateOrgDetail(param);
            } else {
                param.setOrgId(account.getOrgId());
                param.setCreateUser(account.getUserName());
                bnzOrgDetailService.saveOrgDetail(param);
            }

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        }catch (Exception e){
            logger.error("saveOrgDetail", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }

}
