package com.rsd.api;

import com.github.pagehelper.Page;
import com.rsd.domain.Account;
import com.rsd.domain.BnzAccountApply;
import com.rsd.domain.RsdAccountModel;
import com.rsd.service.BnzAccountApplyService;
import com.rsd.service.BnzAccountService;
import com.rsd.utils.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author tony
 * @data 2019-03-21
 * @modifyUser
 * @modifyDate
 */
@Api(value = "用户", description = "用户")
@RestController
@RequestMapping("/bnzAccountApi")
public class BnzAccountApi {

    private static final Logger logger = LoggerFactory.getLogger(BnzAccountApi.class);

    @Autowired
    private BnzAccountService bnzAccountService;

    @Autowired
    private BnzAccountApplyService bnzAccountApplyService;


    @Autowired
    private MessageManager messageManager;

    @Autowired
    private SessionManager sessionManager;


    @ApiOperation(value = "login", notes = "login")
    @PostMapping(value = "/login")
    public HashMap login(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdAccountModel param, HttpServletRequest request) {

        try {
            Map<String,Object> map = new HashMap<>();
            RsdAccountModel accountModel = bnzAccountService.queryAccountByUserName(param);

            if(null!=accountModel
                    && accountModel.getPassword().equals(param.getPassword())
                    && accountModel.getEnabledState()==0
                    && accountModel.getRole().getIsDel()==0){
                Account target = new Account();
                String token = UUID.randomUUID().toString().replaceAll("-","");
                BeanUtils.copyProperties(accountModel,target);
                map.put("user",target);
                map.put("token", token);
                map.put("menu",bnzAccountService.queryUserPermissionRoleByUserId(accountModel.getId()));
                CommonCacheManager.put(token, accountModel, 60 * 30);

                accountModel.setLoginDate(new Date());
                accountModel.setLoginIp(getIp(request));
                bnzAccountService.updateAccount(accountModel);
                return new JsonOut().addMessage(messageManager.getMessage("ERROR.0001")).addDataName("data").addData(map).build();
            } else {
                return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addMessage(messageManager.getMessage("ERROR.0002")).build();
            }
        } catch (Exception e) {
            logger.error("login",e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }



    @ApiOperation(value = "验证登录", notes = "验证登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", required = true, dataType = "String", paramType = "header")})
    @PostMapping(value = "/validateToken")
    public HashMap validateToken(@RequestHeader("token")String token) {
        try {
            if(!StringUtils.isEmpty(token)){
                RsdAccountModel account = (RsdAccountModel) CommonCacheManager.get(token);
                if(account!=null){
                    Map<String,Object> map = new HashMap<>();
                    CommonCacheManager.put(token, account, 60 * 30);
                    Account target = new Account();
                    BeanUtils.copyProperties(account,target);

                    //申请企业 未读数
                    BnzAccountApply accountApply = new BnzAccountApply();
                    accountApply.setIsRead(0);
                    int applyNonReadNum = bnzAccountApplyService.queryAccountApplyNonReadNum(accountApply);

                    map.put("user",target);
                    map.put("token", token);
                    map.put("applyNonReadNum",applyNonReadNum);

                    return new JsonOut().addMessage(messageManager.getMessage("ERROR.0001")).addDataName("data").addData(map).build();
                } else {
                    return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addMessage(messageManager.getMessage("ERROR.0003")).build();
                }
            } else {
                return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addMessage(messageManager.getMessage("ERROR.0003")).build();
            }
        } catch (Exception e) {
            logger.error("validateToken",e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }


    @ApiOperation(value = "queryAccountList", notes = "queryAccountList")
    @PostMapping(value = "/queryAccountList")
    public HashMap queryAccountList(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdAccountModel param) {

        try {
            Page<List> page = bnzAccountService.queryAccountList(param);

            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005"))
                    .addDataName("page")
                    .addData(page.getResult())
                    .addData("pageInfo", new PageInfoWrap(page).get()).build();
        } catch (Exception e) {
            logger.error("queryAccountList",e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }


    @ApiOperation(value = "saveAccount", notes = "saveAccount")
    @PostMapping(value = "/saveAccount")
    public HashMap saveAccount(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdAccountModel param) {
        logger.info("传入参数"+param);
        try{
            RsdAccountModel account = sessionManager.getCurrentUser();
            param.setAccountId(account.getId());
            bnzAccountService.saveAccount(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e){
            logger.error("saveAccount",e);
        }
        return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addException(messageManager.getMessage("ERROR.0008")).build();
    }


    @ApiOperation(value = "updateAccount", notes = "updateAccount")
    @PostMapping(value = "/updateAccount")
    public HashMap updateAccount(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdAccountModel param) {
        logger.info("传入参数"+param);
        try {
            RsdAccountModel account = sessionManager.getCurrentUser();
            param.setAccountId(account.getId());
            bnzAccountService.updateAccount(param);
            return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
        } catch (Exception e) {
            logger.error("updateAccount",e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }


    @ApiOperation(value = "updatePwd", notes = "updatePwd")
    @PostMapping(value = "/updatePwd")
    public HashMap updatePwd(@ApiParam(name = "实体", value = "json格式", required = true) @RequestBody RsdAccountModel param) {

        logger.info("传入参数"+param);

        try {

            RsdAccountModel account = sessionManager.getCurrentUser();
            RsdAccountModel accountModel = bnzAccountService.queryAccountByUserName(param);
            if(null!=accountModel
                    && accountModel.getPassword().equals(param.getOriginPwd())
                    && account.getUserName().equals(accountModel.getUserName())){

                RsdAccountModel bnzAccount = new RsdAccountModel();
                bnzAccount.setId(accountModel.getId());
                bnzAccount.setPassword(param.getPassword());
                bnzAccountService.updateAccount(bnzAccount);
                return new JsonOut().addMessage(messageManager.getMessage("ERROR.0005")).build();
            } else {
                return new JsonOut().addResult(Const.ApiResult.EXCEPTION).addException(messageManager.getMessage("ERROR.0009")).build();
            }

        } catch (Exception e) {
            logger.error("updatePwd",e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }

    }



    private static final String UN_KNOWN = "unknown";

    private static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

}
