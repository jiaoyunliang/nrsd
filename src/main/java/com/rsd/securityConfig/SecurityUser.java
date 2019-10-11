package com.rsd.securityConfig;

import com.rsd.domain.RsdAccount;
import com.rsd.domain.RsdRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tony
 * @data 2019-05-28
 * @modifyUser
 * @modifyDate
 */
public class SecurityUser extends RsdAccount implements UserDetails {


    private static final long serialVersionUID = 8096461333039711652L;

    private List<RsdRole> roles;

    public SecurityUser(RsdAccount account, List<RsdRole> sysRoles){

        if(account != null)
        {
            this.setId(account.getId());
            this.setUserName(account.getUserName());
            this.setPassword(account.getPassword());
            this.setRoles(sysRoles);
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        List<RsdRole> userRoles = this.getRoles();

        if(userRoles != null) {
            authorities = userRoles.stream().map(temp->{
                return new SimpleGrantedAuthority(temp.getName());
            }).collect(Collectors.toList());
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return super.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<RsdRole> getRoles() {
        return roles;
    }

    public void setRoles(List<RsdRole> roles) {
        this.roles = roles;
    }
}
