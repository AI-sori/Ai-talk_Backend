//package com.example.aitalk.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//// 다른 포트의 접근 가능 여부, http 메서드 허용 방식 등을 설정하기 위한 클래스
//@Configuration
//public class CorsMvcConfig {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry corsRegistry) {
//                corsRegistry.addMapping("/**")
//                        .allowedOrigins("http://localhost:5173")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
//                        .allowCredentials(true); // 쿠키 허용
//            }
//        };
//    }
//}
