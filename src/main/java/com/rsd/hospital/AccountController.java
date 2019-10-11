package com.rsd.hospital;

import com.rsd.domain.RsdAccount;
import com.rsd.domain.RsdAccountModel;
import com.rsd.domain.PasswordModel;
import com.rsd.service.BnzAccountService;
import com.rsd.utils.Const;
import com.rsd.utils.HttpSessionManager;
import com.rsd.utils.JsonOut;
import com.rsd.utils.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author tony
 * @data 2019-06-18
 * @modifyUser
 * @modifyDate
 */
@Controller
@RequestMapping("/hospital/account")
public class AccountController {


    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private BnzAccountService bnzAccountService;

    @GetMapping("toChangePasswd")
    public String toChangePasswd() {
        return "/hospital/account/changePasswd";
    }


    @ResponseBody
    @PostMapping(value = "doChangePasswd")
    public HashMap doChangePasswd(@RequestBody PasswordModel passwordModel) {
        try {
            RsdAccount account = HttpSessionManager.get(Const.SESSION_ACCOUNT, RsdAccount.class);

            if (passwordModel.getPasswd().equals(account.getPassword())) {
                if (passwordModel.getNewPasswd().equals(passwordModel.getRepeatPasswd())) {
                    RsdAccountModel param = new RsdAccountModel();
                    param.setId(account.getId());
                    param.setPassword(passwordModel.getNewPasswd());
                    bnzAccountService.updateAccount(param);
                    return new JsonOut().addMessage("密码修改成功!").build();
                } else {
                    return new JsonOut().addResult(Const.ApiResult.BUS_EXCEPTION).addException("俩次密码不一致").build();
                }
            } else {
                return new JsonOut().addResult(Const.ApiResult.BUS_EXCEPTION).addException("原密码错误!").build();
            }
        } catch (Exception e) {
            logger.error("doChangePasswd", e);
            return new JsonOut().addResult(Const.ApiResult.EXCEPTION)
                    .addException(messageManager.getMessage("ERROR.0006")).build();
        }
    }
}
