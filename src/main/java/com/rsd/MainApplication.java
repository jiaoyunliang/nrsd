package com.rsd;

import com.rsd.tag.BnzDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import java.util.Properties;

/**
 * @author tony
 * @data 2019-03-19
 * @modifyUser
 * @modifyDate
 */
//@Configuration
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class MainApplication {


    private Logger logger = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }


    /**重点**/
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.rsd.mapper");
        Properties propertiesMapper = new Properties();
        // 通用mapper位置，不要和其他mapper、dao放在同一个目录
        propertiesMapper.setProperty("mappers", "tk.mybatis.mapper.common.Mapper");
        propertiesMapper.setProperty("notEmpty", "false");
        // 主键UUID回写方法执行顺序,默认AFTER,可选值为(BEFORE|AFTER)
        propertiesMapper.setProperty("ORDER", "BEFORE");
        mapperScannerConfigurer.setProperties(propertiesMapper);
        return mapperScannerConfigurer;
    }

    /**
     * 解决同源策略问题的filter
     * @return
     */
    @Bean
    public Filter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大 //KB,MB
        factory.setMaxFileSize("50240KB");
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("502400KB");
        return factory.createMultipartConfig();
    }

    @Bean
    public BnzDialect bnzDialect(){
        return new BnzDialect();
    }

}
