package com.fzufood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 // 开启swagger2
public class SwaggerConfig {

    // 配置了swagger的Docket的bean实例
    @Bean
    public Docket docket(Environment environment){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("hello")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fzufood.controller"))
                .build();
    }

    private ApiInfo apiInfo(){

        // 作者信息
        Contact contact = new Contact("王帅真","https://wang_shuai_zhen.gitee.io/","978451432@qq.com");

        // 主要就是title和description
        return new ApiInfo(
            "吃点儿啥的API文档",
                "Api Documentation",
                "1.0",
                "https://wang_shuai_zhen.gitee.io/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }
}
