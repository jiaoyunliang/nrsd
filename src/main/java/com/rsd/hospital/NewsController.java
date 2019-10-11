package com.rsd.hospital;

import com.github.pagehelper.Page;
import com.rsd.domain.BnzNews;
import com.rsd.domain.BnzNewsModel;
import com.rsd.service.BnzNewsService;
import com.rsd.utils.Const;
import com.rsd.utils.JsonOut;
import com.rsd.utils.MessageManager;
import com.rsd.utils.PageInfoWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @data 2019-06-10
 * @modifyUser
 * @modifyDate
 */
@Controller
@RequestMapping("/hospital/news")
public class NewsController {


    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);


    @Autowired
    private BnzNewsService bnzNewsService;

    @Autowired
    private MessageManager messageManager;

    @GetMapping(value = "list")
    public String list(@RequestParam(value = "newsType") Integer newsType, Map<String, Object> model) {
        model.put("newsType", newsType);
        return "/hospital/news/list";
    }

    @GetMapping(value = "detail")
    public String queryNewsById(@RequestParam(value = "id") Long id, Map<String, Object> model) {
        try {
            BnzNews news = bnzNewsService.queryNewsById(id);
            model.put("news", news);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "/hospital/news/detail";
    }

    @ResponseBody
    @PostMapping(value = "/queryNewsPage")
    public HashMap queryNewsPage(@RequestBody BnzNewsModel param) {
        try {
            Page<List> page = bnzNewsService.queryNewsList(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page")
                    .addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("queryNewsPage", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }
}
