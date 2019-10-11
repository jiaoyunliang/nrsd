package com.rsd.securityConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsd.utils.RespBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author tony
 * @data 2019-05-28
 * @modifyUser
 * @modifyDate
 */
@Component("logoutSuccessHandler")
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        try {
            User user = (User) authentication.getPrincipal();
            logger.info("USER : " + user.getUsername() + " LOGOUT SUCCESS !  session id = "+request.getSession().getId());
        } catch (Exception e) {
            logger.info("LOGOUT EXCEPTION , e : " + e.getMessage());
        }

        response.setContentType("application/json;charset=utf-8");
        RespBean respBean = RespBean.ok("登出成功!");
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}
