package com.rsd.securityConfig;

import com.rsd.service.impl.CustomUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author tony
 * @data 2018/12/14
 * @modifyUser
 * @modifyDate
 */
//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;


    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);


        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/webjars/springfox-swagger-ui/**");
        web.ignoring().antMatchers("/v2/api-docs/**");


        web.ignoring().antMatchers("/bnzAccountApi/**");
        web.ignoring().antMatchers("/bnzResApi/**");
        web.ignoring().antMatchers("/bnzRoleApi/**");
        web.ignoring().antMatchers("/bnzOrgApi/**");
        web.ignoring().antMatchers("/bnzProductTypeApi/**");
        web.ignoring().antMatchers("/bnzAccountApplyApi/**");
        web.ignoring().antMatchers("/bnzNewsApi/**");
        web.ignoring().antMatchers("/bnzInsideMsgApi/**");
        web.ignoring().antMatchers("/bnzSysNoticeApi/**");
        web.ignoring().antMatchers("/bnzOrgDetailApi/**");
        web.ignoring().antMatchers("/bnzFeedbackApi/**");
        web.ignoring().antMatchers("/bnzAdApi/**");
        web.ignoring().antMatchers("/bnzProductApi/**");
        //忽略登录界面
        web.ignoring().antMatchers("/login");
        web.ignoring().antMatchers("/hospital");
        web.ignoring().antMatchers("/hospital/");
        web.ignoring().antMatchers("/hospital/index");
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll()
                .and()
                .authorizeRequests() // 对请求授权
                .antMatchers("/login","/doLogin","/loginApi/**")
                .permitAll()// 上页面不需要身份认证
                .anyRequest()// 任何请求
                .authenticated()// 都需要身份认证
                .and()
                .csrf().disable()
                .exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler);
                // 禁用跨站攻击
    }



}



