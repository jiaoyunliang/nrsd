package com.rsd.service.impl;

import com.rsd.domain.RsdAccount;
import com.rsd.domain.RsdAccountModel;
import com.rsd.domain.RsdOrgInfo;
import com.rsd.domain.RsdRole;
import com.rsd.mapper.RsdAccountMapper;
import com.rsd.mapper.RsdOrgInfoMapper;
import com.rsd.mapper.RsdRoleMapper;
import com.rsd.utils.Const;
import com.rsd.utils.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tony
 * @data 2019-05-29
 * @modifyUser
 * @modifyDate
 * 企业
 */

@Component("customOrgUserDetailsService")
public class CustomOrgUserDetailsService implements UserDetailsService {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private RsdAccountMapper rsdAccountMapper;

    @Autowired
    private RsdRoleMapper rsdRoleMapper;

    @Autowired
    private RsdOrgInfoMapper rsdOrgInfoMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RsdAccountModel param = new RsdAccountModel();
        param.setUserName(username);
        param.setSysId(Const.E_SYS_ID);
        RsdAccount account = rsdAccountMapper.queryAccountByUserName(param);

        if (account==null){
            logger.error("UserName " + username + " not found");
            throw new UsernameNotFoundException("UserName " + username + " not found");
        } else {

            boolean enabled = true;
            boolean accountNonExpired = true;

            RsdOrgInfo orgInfo = rsdOrgInfoMapper.selectByPrimaryKey(account.getOrgId());

            RsdRole role = rsdRoleMapper.selectByPrimaryKey(account.getRoleId());

            if(role==null || orgInfo==null){
                logger.error("UserName " + username + " not found");
                throw new UsernameNotFoundException("UserName " + username + " not found");
            }

            int days = DateUtil.daysBetween(new Date(),orgInfo.getExpireDate());

            if(days<1){
                accountNonExpired = false;
            }

            if (account.getEnabledState() != 0 || orgInfo.getState()!=0) {
                enabled = false;
            }

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ENTERPRISE");
            grantedAuthorities.add(grantedAuthority);

            return new User(account.getUserName(), account.getPassword(), enabled,accountNonExpired,true,true, grantedAuthorities);
        }

    }

}
