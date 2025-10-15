package com.example.aitalk.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() { // GroupedOpenApi 빈을 통해 API를 그룹화
        return GroupedOpenApi.builder()
                .group("springdoc-public")
                .pathsToMatch("/**") // 모든 경로를 문서화
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() { // OpenAPI 빈을 사용하여 API 문서의 정보(제목, 버전, 설명)를 커스터마이징
        return new OpenAPI()
                .info(new Info()
                        .title("Ai-talk API")
                        .version("v1")
                        .description("Ai-talk API 명세서"));
    }
}
