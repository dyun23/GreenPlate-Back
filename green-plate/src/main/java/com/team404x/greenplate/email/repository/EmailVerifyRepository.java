package com.team404x.greenplate.email.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team404x.greenplate.email.model.entity.EmailVerify;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {
	Optional<EmailVerify> findByEmail(String email);
}
