package com.veli.vshop.seckill.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.veli.vshop.seckill.domain.CommonConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwei
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        ParameterBuilder token = new ParameterBuilder();
        token.name(CommonConstants.AUTH_TOKEN).description("token值")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build();

        List<Parameter> params = new ArrayList<>();
        params.add(token.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.veli.vshop.seckill"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(params);
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("vShop APIs")
                .version("1.0")
                .build();
    }
}
