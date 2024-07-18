package com.team404x.greenplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.team404x.greenplate.config.filter.jwt.JwtFilter;
import com.team404x.greenplate.utils.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity // Web관련 Security
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtUtil jwtUtil;

	// pw를 암호화하기 위해 사용
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

//  카카오페이 테스트시 필요하여 넣음
//	@Bean
//	public CorsFilter corsFilter() {
//		CorsConfiguration config = new CorsConfiguration();
//
//		config.addAllowedOrigin("http://localhost:63342"); // 허용할 출처
//		config.addAllowedOrigin("http://localhost:8080"); // 허용할 출처
//		config.addAllowedMethod("*"); // 허용할 메서드 (GET, POST, PUT 등)
//		config.addAllowedHeader("*"); // 허용할 헤더
//		config.setAllowCredentials(true); // 자격 증명 허용
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", config);
//
//		return new CorsFilter(source);
//	}

	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
		http.csrf((auth) -> auth.disable());

		http.httpBasic((auth) -> auth.disable());

		http.formLogin(Customizer.withDefaults());

		http.authorizeHttpRequests((auth) ->
				auth.requestMatchers( "/**").permitAll()
					.anyRequest().authenticated()
		);

		http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}

