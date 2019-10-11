package com.rsd.api;

import com.rsd.domain.RsdRes;
import com.rsd.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author tony
 * @data 2019-03-20
 * @modifyUser
 * @modifyDate
 */
@Api(value = "user", description = "测试")
@RestController
@RequestMapping("/userApi")
public class UserApi {

    @Resource
    private MessageSource messageSource;

    @ApiOperation(value = "验证登录", notes = "用户登录API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", required = true, dataType = "String", paramType = "header")})
    @PostMapping(value = "/validateToken")
    public RespBean validateToken(@RequestHeader("token")String token) throws Exception {
        RespBean respBean;
        if(!StringUtils.isEmpty(token)){
            if(token.equals("123")){
                List<RsdRes> list = new ArrayList<RsdRes>();

                List<RsdRes> list1 = new ArrayList<RsdRes>();

                RsdRes br =  new RsdRes();
                br.setId(1L);
                br.setResName("manager");
                br.setResType("2");
                br.setResComponent("Manager");
                br.setResCode("0001");
                br.setResUrl("manager");


                RsdRes bt =  new RsdRes();
                bt.setId(2L);
                bt.setResName("button");
                bt.setResType("3");
                bt.setResCode("00010001");
                list1.add(bt);

                bt =  new RsdRes();
                bt.setId(3L);
                bt.setResName("qua");
                bt.setResType("2");
                bt.setResComponent("bnzQua/list");
                bt.setResCode("00010002");
                bt.setResUrl("qua");

                RsdRes bx =  new RsdRes();
                bx.setId(4L);
                bx.setResName("button");
                bx.setResType("3");
                bx.setResCode("000100020001");
                List<RsdRes> list3 = new ArrayList<RsdRes>();
                list3.add(bx);
                bt.setChildren(list3);
                list1.add(bt);

                br.setChildren(list1);
                list.add(br);

                respBean = RespBean.ok(messageSource.getMessage("ERROR.0001", null, Locale.CHINA),list);
            } else {
                respBean = RespBean.error(messageSource.getMessage("ERROR.0002", null, Locale.CHINA));
            }
        } else {
            respBean = RespBean.error(messageSource.getMessage("ERROR.0002", null, Locale.CHINA));
        }

        return respBean;
    }
}
