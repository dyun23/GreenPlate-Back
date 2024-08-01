package com.team404x.greenplate.email.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.team404x.greenplate.common.GlobalMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
	private final RedisTemplate<String, String> redisTemplate;
	private final JavaMailSender emailSender;

	public void saveEmail(String email, String role) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		SimpleMailMessage message = new SimpleMailMessage();
		String uuid = UUID.randomUUID().toString();
		message.setTo(email);
		message.setSubject(GlobalMessage.EMAIL_TITLE.getMessage());
		message.setText(GlobalMessage.EMAIL_URL_TEMPLATE.formatUrl(email, uuid, role));
		emailSender.send(message);
		valueOperations.set(email, uuid, 180, TimeUnit.SECONDS);
	}

	public boolean isExistEmail(String email, String uuid) {
		ValueOperations<String, String> valOperations = redisTemplate.opsForValue();
		String code = valOperations.get(email);
		return code != null && code.equals(uuid);
	}
}
