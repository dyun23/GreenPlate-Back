package com.team404x.greenplate.utils.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
	}

	public String getRole(String token) {

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
	}

	public Boolean isExpired(String token) {

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
	}

	public String createToken(String email, String role) {
		return Jwts.builder()
			.claim("email", email) // 토근에 담을 데이터
			.claim("role", role)
			.issuedAt(new Date(System.currentTimeMillis())) // 언제 발행됐는지
			.expiration(new Date(System.currentTimeMillis()+ 5*60*1000))
			.signWith(secretKey) // 우리만 알 수 있는 secretKey를 가지고 토큰을 생성함
			.compact();
	}
}
