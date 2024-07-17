package com.team404x.greenplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.team404x.greenplate.config.filter.login.LoginFilter;
import com.team404x.greenplate.utils.jwt.JwtUtil;

@Configuration
@EnableWebSecurity // Web관련 Security
public class SecurityConfig {
	private final JwtUtil jwtUtil;
	private final AuthenticationConfiguration authenticationConfiguration;

	public SecurityConfig(JwtUtil jwtUtil, AuthenticationConfiguration authenticationConfiguration) {
		this.jwtUtil = jwtUtil;
		this.authenticationConfiguration = authenticationConfiguration;
	}

	// pw를 암호화하기 위해 사용
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
		http.csrf((auth) -> auth.disable());

		http.httpBasic((auth) -> auth.disable());

		http.formLogin(Customizer.withDefaults());

		http.authorizeHttpRequests((auth) ->
				auth.requestMatchers( "/**").permitAll()
					.anyRequest().authenticated()
		);
		

		http.addFilterAt(new LoginFilter(jwtUtil, authenticationManager(authenticationConfiguration)),
			UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}

