package com.rsd.interceptor;

import com.rsd.utils.HttpSessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * @author tony
 * @data 2019-06-17
 * @modifyUser
 * @modifyDate
 */
public class BnzWebRequestInterceptor implements WebRequestInterceptor {

    private static Log logger = LogFactory.getLog(BnzWebRequestInterceptor.class);

    @Override
    public void preHandle(WebRequest request) throws Exception {

    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
//        ServletWebRequest webreq = (ServletWebRequest)request;
//        logger.info("---------------------"+webreq.getRequest().getRequestURI());
        HttpSessionManager.refresh();
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }
}
