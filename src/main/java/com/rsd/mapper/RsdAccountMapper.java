package com.rsd.mapper;

import com.rsd.domain.RsdAccount;
import com.rsd.domain.RsdAccountModel;
import com.rsd.domain.RsdRes;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RsdAccountMapper extends Mapper<RsdAccount> {


    RsdAccountModel queryAccountByUserName(@Param("model") RsdAccountModel model);

    List<RsdRes> queryUserPermissionRoleByUserId(@Param("accountId") Long accountId);

    List<RsdAccountModel> queryAccountList(@Param("model") RsdAccountModel model);

    Long getId();

    int updateAccountIsService();

    int updateAccountRoleByOrgId(@Param("model") RsdAccount model);
}