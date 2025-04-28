package com.example.aitalk.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 다른 포트의 접근 가능 여부, http 메서드 허용 방식 등을 설정하기 위한 클래스
@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE") // PATCH(일부만 업데이트)를 제외한 모든 http 메서드를 허용
                .allowCredentials(true); // 쿠키 허용 (토큰을 이용한 방식의 로그인일 경우, 쿠키 사용을 해야해서 허용 처리)
    }
}
