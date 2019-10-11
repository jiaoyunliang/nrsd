package com.rsd.interceptor;

import com.alibaba.fastjson.JSON;
import com.rsd.domain.RsdAccountModel;
import com.rsd.utils.CommonCacheManager;
import com.rsd.utils.RespBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author tony
 * @data 2019-03-27
 * @modifyUser
 * @modifyDate
 */
public class UserInterceptor implements HandlerInterceptor {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Resource
    private MessageSource messageSource;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Content-Type", "application/json");
        String token = request.getHeader("token");
        logger.info("请求开始---------token:"+token);

        if (token != null && !"".equals(token)) {
            RsdAccountModel account = (RsdAccountModel) CommonCacheManager.get(token);
            if (null!=account) {
                CommonCacheManager.put(token, account, 60 * 30);
                return true;
            } else {
                RespBean respBean = RespBean.unauthorized(messageSource.getMessage("ERROR.0003", null, Locale.CHINA));
                response.setStatus(402);
                response.getWriter().write(JSON.toJSONString(respBean));
                return false;
            }
        } else {
            RespBean respBean = RespBean.timeOut(messageSource.getMessage("ERROR.0003", null, Locale.CHINA));
            response.setStatus(402);
            response.getWriter().write(JSON.toJSONString(respBean));
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
