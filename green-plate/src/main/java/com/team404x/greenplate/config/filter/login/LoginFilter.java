package com.team404x.greenplate.config.filter.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.team404x.greenplate.utils.jwt.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	private final JwtUtil jwtUtil;
	private final AuthenticationManager authenticationManager;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		CustomUserDetails user = (CustomUserDetails)authResult.getPrincipal(); // 데이터 가져오기
		Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
		// 아직 권한 하나밖에 없기 때문에 .next()하나만 실행
		GrantedAuthority auth = authorities.iterator().next();
		String username = user.getUsername();
		String role = auth.getAuthority();

		String token = jwtUtil.createToken(username, role);
		System.out.println(token);
		response.addHeader("Authorization", "Bearer" + token);

		PrintWriter out = response.getWriter();
		out.println("{\"isSuccess\": true, \"accessToken\": \""+token+"\"}");
	}
}
