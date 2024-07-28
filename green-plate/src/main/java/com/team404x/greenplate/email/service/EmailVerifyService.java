package com.team404x.greenplate.email.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.team404x.greenplate.common.GlobalMessage;
import com.team404x.greenplate.email.model.entity.EmailVerify;
import com.team404x.greenplate.email.repository.EmailVerifyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
	private final EmailVerifyRepository emailVerifyRepository;
	private final JavaMailSender emailSender;


	public void sendEmail(String email, String role) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject(GlobalMessage.EMAIL_TITLE.getMessage());
		String uuid = UUID.randomUUID().toString();
		save(email, uuid);
		message.setText(GlobalMessage.EMAIL_URL_TEMPLATE.formatUrl(email, uuid, role));
		emailSender.send(message);
	}

	public boolean isExist(String email, String uuid) {
		Optional<EmailVerify> result = emailVerifyRepository.findByEmail(email);
		if(result.isPresent()) {
			EmailVerify emailVerify = result.get();
			if(emailVerify.getUuid().equals(uuid)) {
				return true;
			}
		}
		return false;
	}

	private void save(String email, String uuid) {
		EmailVerify emailVerify = EmailVerify.builder()
			.email(email)
			.uuid(uuid)
			.build();

		emailVerifyRepository.save(emailVerify);
	}
}
