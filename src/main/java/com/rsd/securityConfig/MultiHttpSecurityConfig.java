package com.rsd.securityConfig;

import com.rsd.service.impl.CustomOrgUserDetailsService;
import com.rsd.service.impl.CustomUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author tony
 * @data 2019-05-28
 * @modifyUser
 * @modifyDate
 */
@EnableWebSecurity
@Configuration
public class MultiHttpSecurityConfig {

    @Configuration
    @Order(1)
    public static class HospitalWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

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
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customUserDetailsService);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            super.configure(web);

            web.ignoring().antMatchers("/js/**");
            web.ignoring().antMatchers("/icomoon/**");
            web.ignoring().antMatchers("/hospital/css/**");
            web.ignoring().antMatchers("/hospital/images/**");
            web.ignoring().antMatchers("/hospital/js/**");
            web.ignoring().antMatchers("/lib/**");

            web.ignoring().antMatchers("/enterprise/css/**");
            web.ignoring().antMatchers("/enterprise/images/**");
            web.ignoring().antMatchers("/enterprise/js/**");

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
//            web.ignoring().antMatchers("/hospital");
//            web.ignoring().antMatchers("/hospital/index");
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/hospital/**")
                    .formLogin()
                    .loginPage("/hospital/index")
                    .loginProcessingUrl("/hospital/doLogin")
                    .successHandler(loginSuccessHandler)
                    .failureHandler(loginFailureHandler)
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/hospital/logout")
                    .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .permitAll()
                    .and()
                    .sessionManagement()
                    .invalidSessionUrl("/hospital/expired")
                    .maximumSessions(1)
                    .expiredUrl("/hospital/expired")
                    .and()
                    .and()
                    .authorizeRequests() // 对请求授权
                    .antMatchers("/hospital",
                            "/hospital/index",
                            "/hospital/doLogin",
                            "/hospital/expired",
                            "/hospital/product/**",
                            "/hospital/news/**")
                    .permitAll()// 上页面不需要身份认证
                    .anyRequest()// 任何请求
                    .hasRole("HOSPITAL")
//                    .authenticated()// 都需要身份认证
                    .and()
                    .csrf().disable()
                    .exceptionHandling().accessDeniedPage("/hospital/index");
                    //.accessDeniedHandler(authenticationAccessDeniedHandler);
            // 禁用跨站攻击
        }
    }



    @Configuration
    @Order(2)
    public static class OrgWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        protected final Log logger = LogFactory.getLog(this.getClass());

        @Autowired
        private CustomOrgUserDetailsService customOrgUserDetailsService;

        @Autowired
        private AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

        @Autowired
        private LoginFailureHandler loginFailureHandler;

        @Autowired
        private LoginSuccessHandler loginSuccessHandler;

        @Autowired
        private LogoutSuccessHandler logoutSuccessHandler;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customOrgUserDetailsService);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            super.configure(web);

            //忽略登录界面
//            web.ignoring().antMatchers("/enterprise");
//            web.ignoring().antMatchers("/enterprise/index");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/enterprise/**")
                    .formLogin()
                    .loginPage("/enterprise/index")
                    .loginProcessingUrl("/enterprise/doLogin")
                    .successHandler(loginSuccessHandler)
                    .failureHandler(loginFailureHandler)
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/enterprise/logout")
                    .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .permitAll()
                    .and()
                    .sessionManagement()
                    .invalidSessionUrl("/enterprise/expired")
                    .maximumSessions(1)
                    .expiredUrl("/enterprise/expired")
                    .and()
                    .and()
                    .authorizeRequests() // 对请求授权
                    .antMatchers("/enterprise",
                            "/enterprise/index",
                            "/enterprise/doLogin",
                            "/enterprise/expired",
                            "/enterprise/product/view",
                            "/enterprise/accountApply",
                            "/enterprise/news/**")
                    .permitAll()// 上页面不需要身份认证
                    .anyRequest()// 任何请求
                    .hasRole("ENTERPRISE")
//                    .authenticated()// 都需要身份认证
                    .and()
                    .csrf().disable()
                    .exceptionHandling().accessDeniedPage("/enterprise/index");
                    //.accessDeniedHandler(authenticationAccessDeniedHandler);
            // 禁用跨站攻击
        }
    }
}
