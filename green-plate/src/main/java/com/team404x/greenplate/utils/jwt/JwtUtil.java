package com.team404x.greenplate.utils.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.team404x.greenplate.common.GlobalMessage;

import io.jsonwebtoken.Jwts;

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

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
	}

	public String createToken(Long id, String email, String role) {
		return Jwts.builder()
			.claim(GlobalMessage.JWT_ID.getMessage(), id)
			.claim(GlobalMessage.JWT_EMAIL.getMessage(), email) // 토큰에 담을 데이터
			.claim(GlobalMessage.JWT_ROLE.getMessage(), role)
			.issuedAt(new Date(System.currentTimeMillis())) // 언제 발행됐는지
			.expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
			.signWith(secretKey) // 우리만 알 수 있는 secretKey를 가지고 토큰을 생성함
			.compact();
	}
}
