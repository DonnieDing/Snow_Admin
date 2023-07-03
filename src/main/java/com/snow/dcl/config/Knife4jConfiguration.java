package com.snow.dcl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @ClassName Knife4jConfiguration
 * (功能描述)
 * Knife4j配置类
 * @Author Dcl_Snow
 * @Create 2021/8/25 10:33
 * @version 1.0.0
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {
    @Bean
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(new ApiInfoBuilder()
                     .description("Snow_Admin RESTful APIs")
                     .termsOfServiceUrl("http://dcl_snow.gitee.io/")
                     .contact("Dcl_Snow")
                     .version("1.0")
                     .build())
            //分组名称
            .groupName("Snow_Admin v1.0")
            .select()
            //指定项目中Controller扫描包路径
            .apis(RequestHandlerSelectors.basePackage("com.snow.dcl.controller"))
            .paths(PathSelectors.any())
            .build();
        return docket;
    }
}
