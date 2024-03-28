package com.wlld.myjecs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @param
 * @DATA
 * @Author LiDaPeng
 * @Description
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wlld.myjecs.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("myJesc智能客服管理(my Java easyAi customer server)")
                        .description("基于easyAi算法引擎可以给任意服务平台提供自动化客服生成订单服务")
                        .version("1.0")
                        .contact(new Contact("智能客服", "gitee.com/ldp_dpsmax/easyAi", "794757862@qq.com"))
                        .license("The Apache License")
                        .licenseUrl("https://gitee.com/ldp_dpsmax/easyAi")
                        .build());
    }
}


