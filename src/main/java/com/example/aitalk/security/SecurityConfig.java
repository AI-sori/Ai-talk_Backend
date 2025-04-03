package com.example.aitalk.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

// Cannot resolve symbol 'AbstractHttpConfigurer' (Spring Security 6 이상을 사용할 때 발생)
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // form 인증 비활성화(form 기반 인증을 할것인지를 체크)
        // Rest-API를 통해 JSON으로 통신해 발급받은 token으로 인증 유효성을 따질것이므로 필요가 없어서 disable
        http.formLogin(AbstractHttpConfigurer::disable);

        // HTTP 기본 인증 비활성화
        // 기본 로그인 창을 쓰지 않을것이기 때문에 역시 disable 처리
        http.httpBasic(AbstractHttpConfigurer::disable);

        // CSRF 공격 방어 기능 비활성화(csrf의 사용 여부를 체크)
        // Rest-API를 사용하여 통신하므로 session 기반 인증이 아닌 무상태(statelessful) 방식의 인증이기 때문에 disable
        http.csrf(AbstractHttpConfigurer::disable);

        //경로별 인가 작업(경로별 접근시 인증 여부를 확인하는 설정)

        http
                .authorizeHttpRequests((auth) -> auth //우선은 인증없이 접근가능하게끔 모두 .permitAll()로 설정
//                        .requestMatchers("/").permitAll()
                        .anyRequest().permitAll());

        // 세션 관리 정책 설정 -> 세션 인증을 사용하지 않고 JWT를 사용하여 인증하기 때문에 세션 불필요
        http.sessionManagement(management -> management // sessionManagement() -> csrf와 동일한 이유
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}