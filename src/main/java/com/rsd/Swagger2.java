package com.rsd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author tony
 * @data 2019-03-19
 * @modifyUser
 * @modifyDate
 */
//http://localhost:7777/swagger-ui.html
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rsd.api"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("测试 接口说明").version("2.0").build();

    }

}