package com.rsd.hospital;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.pagehelper.Page;
import com.rsd.domain.BnzAd;
import com.rsd.domain.BnzNewsModel;
import com.rsd.domain.BnzProductModel;
import com.rsd.domain.BnzProductTypeModel;
import com.rsd.service.BnzAdService;
import com.rsd.service.BnzNewsService;
import com.rsd.service.BnzProductService;
import com.rsd.service.BnzProductTypeService;
import com.rsd.utils.Const;
import com.rsd.utils.MessageManager;
import com.rsd.utils.PageInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @data 2019-05-28
 * @modifyUser
 * @modifyDate
 */
@Controller
@RequestMapping("/hospital")
@PropertySource(value = {"classpath:upload.properties"},encoding="utf-8")
public class IndexController {


    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BnzNewsService bnzNewsService;

    @Autowired
    private BnzProductTypeService bnzProductTypeService;

    @Autowired
    private BnzProductService bnzProductService;

    @Autowired
    private BnzAdService bnzAdService;

    @Value("${ueditorFileUrlPerfix}")
    private String fileUrlPrefix;

    @RequestMapping(value = {"","/", "/index"})
    public String index(Map<String, Object> model, HttpSession session) {

        try {
            PageInput pageInput = new PageInput();
            pageInput.setCurrent(1);
            pageInput.setSize(4);

            //新闻 政府文件
            BnzNewsModel newsParam = new BnzNewsModel();
            newsParam.setPageInput(pageInput);
            newsParam.setSysId(Const.H_SYS_ID);
            newsParam.setNewsType(4);
            Page<List> page = bnzNewsService.queryNewsList(newsParam);
            model.put("news4",page.getResult());

            //新闻 工作动态
            newsParam = new BnzNewsModel();
            newsParam.setPageInput(pageInput);
            newsParam.setSysId(Const.H_SYS_ID);
            newsParam.setNewsType(5);
            page = bnzNewsService.queryNewsList(newsParam);
            model.put("news5",page.getResult());

            //新闻 资料下载
            newsParam = new BnzNewsModel();
            newsParam.setPageInput(pageInput);
            newsParam.setSysId(Const.H_SYS_ID);
            newsParam.setNewsType(6);
            page = bnzNewsService.queryNewsList(newsParam);
            model.put("news6",page.getResult());

            //新闻 企业评价
            newsParam = new BnzNewsModel();
            newsParam.setPageInput(pageInput);
            newsParam.setSysId(Const.H_SYS_ID);
            newsParam.setNewsType(7);
            page = bnzNewsService.queryNewsList(newsParam);
            model.put("news7",page.getResult());

            //产品类别
            List<BnzProductTypeModel> productTypeModelList = bnzProductTypeService.queryProductType();
            Map<String,List> productTypeMap = new HashMap<>();
            for (BnzProductTypeModel p: productTypeModelList){
                if(productTypeMap.containsKey(p.getGroupId()+"")){
                    productTypeMap.get(p.getGroupId()+"").add(p);
                } else {
                    List<BnzProductTypeModel> tmp = new ArrayList<>();
                    tmp.add(p);
                    productTypeMap.put(p.getGroupId()+"",tmp);
                }
            }
            for(Map.Entry<String,List> entry:productTypeMap.entrySet()){
                model.put("productGroup"+entry.getKey(), entry.getValue());
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


            //ad
            BnzAd adParam = new BnzAd();
            adParam.setSysId(Const.H_SYS_ID);
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

            //keyword
            if(session.getAttribute("keyword")==null){
                adParam = new BnzAd();
                adParam.setSysId(Const.H_SYS_ID);
                adParam.setStatus(0);
                adParam.setType(3);
                List<BnzAd> kwList = bnzAdService.queryAdList(adParam);
                session.setAttribute("keyword",kwList);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        model.put("fileServer", fileUrlPrefix);

        return "/hospital/index";
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

                map.put("result",Const.ApiResult.TOKEN_PAST_DUE);
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
            return "/hospital/expired";
        }

    }

}
