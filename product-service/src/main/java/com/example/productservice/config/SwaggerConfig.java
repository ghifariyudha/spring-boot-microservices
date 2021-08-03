package com.example.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static List<Parameter> parameters() {
        List<Parameter> parameters = new ArrayList<>();
        Parameter token = new ParameterBuilder()
                .name(HttpHeaders.AUTHORIZATION)
                .parameterType("header")
                .modelRef(new ModelRef("string"))
                .required(true)
                .description("Bearer Token")
                .defaultValue("Bearer")
                .order(0)
                .build();
        parameters.add(token);
        return parameters;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.productservice"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters());
    }
}
