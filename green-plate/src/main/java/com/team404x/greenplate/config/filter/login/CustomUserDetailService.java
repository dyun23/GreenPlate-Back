package com.team404x.greenplate.config.filter.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team404x.greenplate.user.entity.User;
import com.team404x.greenplate.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
	private final UserRepository userRepository;

	/*
	TODO
	1. 예외처리 하기
	2. company가 들어온 경우 처리하기
	* */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (email.endsWith("_user")) {
			String emailInput = email.split("_user")[0];
			User user = userRepository.findUserByEmail(emailInput);
			return new CustomUserDetails(user);
		}
		return null;
	}
}
