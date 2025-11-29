package com.example.aitalk.global.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(
			"http://localhost:5173",
			"https://ai-talkk.netlify.app",
			"http://localhost:8080",
			"http://127.0.0.1:8080",
			"http://15.165.102.27:8080",
			"https://aitalk.kro.kr"
		));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
			.formLogin(form -> form.disable())
			.httpBasic(httpBasic -> httpBasic.disable())

			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint((request, response, authException) -> handleUnauthorized(response))
				.accessDeniedHandler((request, response, accessDeniedException) -> handleForbidden(response))
			)

			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(
					"/",
					"/members/join",
					"/members/login",
					"/swagger-ui/**",
					"/v3/api-docs/**",
					"/api-docs/**",
					"/api/ai/**"
				).permitAll()
				.anyRequest().authenticated());

		return http.build();
	}

	// 401 Unauthorized (인증 실패): 로그인이 필요
	private void handleUnauthorized(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json;charset=UTF-8");
		String jsonResponse = String.format(
			"{\"code\": %d, \"msg\": \"%s\", \"data\": null}",
			HttpServletResponse.SC_UNAUTHORIZED,
			"로그인이 필요합니다."
		);
		response.getWriter().write(jsonResponse);
	}

	// 403 Forbidden (권한 부족): 접근 권한이 없음
	private void handleForbidden(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json;charset=UTF-8");
		String jsonResponse = String.format(
			"{\"code\": %d, \"msg\": \"%s\", \"data\": null}",
			HttpServletResponse.SC_FORBIDDEN,
			"접근 권한이 없습니다."
		);
		response.getWriter().write(jsonResponse);
	}
}
