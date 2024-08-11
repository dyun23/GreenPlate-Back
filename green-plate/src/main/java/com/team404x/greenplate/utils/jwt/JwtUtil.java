package com.team404x.greenplate.utils.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import com.team404x.greenplate.common.GlobalMessage;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;

@Component
public class JwtUtil {
	private final SecretKey secretKey;

	public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm()
		);
	}

	public String getEmail(String token) {

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(GlobalMessage.JWT_EMAIL.getMessage(), String.class);
	}

	public String getRole(String token) {

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(GlobalMessage.JWT_ROLE.getMessage(), String.class);
	}

	public Long getId(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(GlobalMessage.JWT_ID.getMessage(), Long.class);
	}

	public Boolean isExpired(String token) {
		try {
			return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
		} catch (Exception e) {
			return true;
		}
	}

	public String createToken(Long id, String email, String role) {
		return Jwts.builder()
			.claim(GlobalMessage.JWT_ID.getMessage(), id)
			.claim(GlobalMessage.JWT_EMAIL.getMessage(), email) // 토큰에 담을 데이터
			.claim(GlobalMessage.JWT_ROLE.getMessage(), role)
			.issuedAt(new Date(System.currentTimeMillis())) // 언제 발행됐는지
			.expiration(new Date(System.currentTimeMillis() + 60 * 1000))
			.signWith(secretKey) // 우리만 알 수 있는 secretKey를 가지고 토큰을 생성함
			.compact();
	}

	public Cookie createCookie(String token) {
		Cookie cookie = new Cookie(GlobalMessage.ACCESS_TOKEN.getMessage(), token);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		return cookie;
	}

	public Cookie removeCookie() {
		Cookie cookie = new Cookie(GlobalMessage.ACCESS_TOKEN.getMessage(), null);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		return cookie;
	}
}
