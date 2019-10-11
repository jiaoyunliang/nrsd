package com.rsd.securityConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsd.domain.*;
import com.rsd.mapper.BnzAccountMapper;
import com.rsd.mapper.BnzOrgInfoMapper;
import com.rsd.mapper.BnzRoleMapper;
import com.rsd.service.BnzSysNoticeService;
import com.rsd.utils.Const;
import com.rsd.utils.HttpSessionManager;
import com.rsd.utils.RespBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author tony
 * @data 2019-05-28
 * @modifyUser
 * @modifyDate
 */
@Component("loginSuccessHandler")
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private BnzAccountMapper bnzAccountMapper;

    @Autowired
    private BnzRoleMapper bnzRoleMapper;

    @Autowired
    private BnzOrgInfoMapper bnzOrgInfoMapper;

    @Autowired
    private BnzSysNoticeService bnzSysNoticeService;

    public LoginSuccessHandler(){
        super();
        setUseReferer(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        User user = (User) authentication.getPrincipal();
        //输出登录提示信息
        logger.debug("登录账户 :" + user.getUsername());
        logger.debug("IP :"+getIpAddress(request));
        logger.debug("session timeout :"+request.getSession().getMaxInactiveInterval());

        RsdAccountModel param = new RsdAccountModel();
        param.setUserName(user.getUsername());

        //账户信息
        RsdAccount account = bnzAccountMapper.queryAccountByUserName(param);
        HttpSessionManager.put(Const.SESSION_ACCOUNT, account);

        //账户机构信息
        RsdOrgInfo orgInfo = bnzOrgInfoMapper.selectByPrimaryKey(account.getOrgId());
        HttpSessionManager.put(Const.SESSION_ACCOUNT_ORG, orgInfo);

        //账户 角色
        RsdRole role = bnzRoleMapper.selectByPrimaryKey(account.getRoleId());
        HttpSessionManager.put(Const.SESSION_ACCOUNT_ROLE, role);


        try {
            //账户 通知未读数
            BnzSysNoticeModel param1= new BnzSysNoticeModel();
            param1.setSysId(account.getSysId());
            param1.setOrgId(account.getOrgId());
            int unreadNum = bnzSysNoticeService.queryNoticeUnreadNum(param1);
            request.getSession().setAttribute("noticeUnreadNum",unreadNum);
        } catch (Exception e) {
            logger.error("loginSuccessHandler",e);
        }

        param = new RsdAccountModel();
        param.setSysId(Const.M_SYS_ID);
        param.setIsService(1);
        param.setEnabledState(0);

        List list = bnzAccountMapper.select(param);

        if(!list.isEmpty()){
            request.getSession().setAttribute(Const.SESSION_SERVICE_ACCOUNT,list.get(0));
        }

        //账户 资源
        List<RsdRes> resList =  bnzAccountMapper.queryUserPermissionRoleByUserId(account.getId());
        List<RsdRes> result = new ArrayList<>();
        for (RsdRes data0 : resList) {
            boolean mark = true;
            for (RsdRes data1 : resList) {
                if (data1.getParentCode() != null && data0.getParentCode().equals(data1.getResCode())) {
                    mark = false;
                    if (data1.getChildren() == null) {
                        data1.setChildren(new ArrayList<>());
                    }
                    data1.getChildren().add(data0);
                    break;
                }
            }
            if (mark) {
                result.add(data0);
            }
        }
        request.getSession().setAttribute(Const.SESSION_ACCOUNT_RES,result);

        //账户 资源 编码
        Map<String,RsdRes> hadResMap = new HashMap<String,RsdRes>();
        for (RsdRes res : resList){
            hadResMap.put("r_"+res.getResCode(),res);
        }
        HttpSessionManager.put(Const.SESSION_ACCOUNT_RES, hadResMap);

        RsdAccount updateAccount = new RsdAccount();
        updateAccount.setId(account.getId());
        updateAccount.setLoginCount((account.getLoginCount()==null?0:account.getLoginCount())+1);
        updateAccount.setLoginDate(new Date());
        updateAccount.setLoginIp(getIpAddress(request));
        bnzAccountMapper.updateByPrimaryKeySelective(updateAccount);

        String loginType = request.getHeader("Accept");

        logger.debug("---------"+loginType);

        if (request.getHeader("Accept").indexOf("application/json") > -1) {
            response.setContentType("application/json;charset=utf-8");
            RespBean respBean = RespBean.ok("登录成功!",resList);
            ObjectMapper om = new ObjectMapper();
            PrintWriter out = response.getWriter();
            out.write(om.writeValueAsString(respBean));
            out.flush();
            out.close();
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    public String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
