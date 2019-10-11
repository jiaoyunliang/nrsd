package com.rsd.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author tony
 * @data 2019-03-27
 * @modifyUser
 * @modifyDate
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {


    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    public BnzWebRequestInterceptor bnzWebRequestInterceptor(){
        return new BnzWebRequestInterceptor();
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(userInterceptor())
                .addPathPatterns("/bnzAccountApi/**")
                .addPathPatterns("/bnzResApi/**")
                .addPathPatterns("/bnzRoleApi/**")
                .addPathPatterns("/bnzOrgApi/**")
                .addPathPatterns("/bnzProductTypeApi/**")
                .addPathPatterns("/bnzAccountApplyApi/**")
                .addPathPatterns("/bnzNewsApi/**")
                .addPathPatterns("/bnzInsideMsgApi/**")
                .addPathPatterns("/bnzSysNoticeApi/**")
                .addPathPatterns("/bnzOrgDetailApi/**")
                .addPathPatterns("/bnzFeedbackApi/**")
                .addPathPatterns("/bnzAdApi/**")
                .addPathPatterns("/bnzProductApi/**")
                .addPathPatterns("/file/upload")
                .excludePathPatterns("/bnzAccountApi/login");

        registry.addWebRequestInterceptor(bnzWebRequestInterceptor())
                .excludePathPatterns("/hospital/doLogin")
                .excludePathPatterns("/hospital/logout")
                .excludePathPatterns("/hospital/index")
                .excludePathPatterns("/hospital/news/**")
                .excludePathPatterns("/enterprise/doLogin")
                .excludePathPatterns("/enterprise/index")
                .excludePathPatterns("/enterprise/logout")
                .excludePathPatterns("/enterprise/news/**")
                .addPathPatterns("/hospital/**")
                .addPathPatterns("/enterprise/**");

        super.addInterceptors(registry);
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(fastJsonHttpMessageConverterEx());
//        super.configureMessageConverters(converters);
//    }
//
//    @Bean
//    public FastJsonHttpMessageConverterEx fastJsonHttpMessageConverterEx() {
//        return new FastJsonHttpMessageConverterEx();
//    }
}
