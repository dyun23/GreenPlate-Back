package com.team404x.greenplate.config.filter.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team404x.greenplate.common.GlobalMessage;
import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.company.repository.CompanyRepository;
import com.team404x.greenplate.user.model.entity.User;
import com.team404x.greenplate.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
	private final UserRepository userRepository;
	private final CompanyRepository companyRepository;

	/*
	TODO
	1. user 존재하지 않거나 enabled = false 일때 예외처리 하기
	2. company가 들어온 경우 처리하기
	* */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		String emailInput;
		if (email.endsWith(GlobalMessage.USER_SUFFIX.getMessage())) {
			emailInput = email.replace(GlobalMessage.USER_SUFFIX.getMessage(), "");
			User user = userRepository.findUserByEmailAndEnabled(emailInput, true);
			return user == null ? null : new CustomUserDetails(user);
		} else if (email.endsWith(GlobalMessage.COMPANY_SUFFIX.getMessage())) {
			emailInput = email.replace(GlobalMessage.COMPANY_SUFFIX.getMessage(), "");
			Company company = companyRepository.findCompanyByEmail(emailInput);
			return new CustomUserDetails(company);
		}
		return null;
	}
}
