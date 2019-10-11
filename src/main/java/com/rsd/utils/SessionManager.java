package com.rsd.utils;

import com.rsd.domain.RsdAccountModel;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tony
 * @data 2019-04-01
 * @modifyUser
 * @modifyDate
 */
@Component
public class SessionManager {

    private final static Logger logger = LogManager.getLogger(SessionManager.class);


    @Autowired
    private HttpServletRequest request;

    public RsdAccountModel getCurrentUser(){

        String token = request.getHeader("token");
        token = token == null ? request.getParameter("token") : token;

        RsdAccountModel account = (RsdAccountModel) CommonCacheManager.get(token);

        return account;

    }
}
