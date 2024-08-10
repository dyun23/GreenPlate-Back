package com.team404x.greenplate.company.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.team404x.greenplate.common.GlobalMessage;
import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.company.model.request.CompanyLoginReq;
import com.team404x.greenplate.company.model.request.CompanySignupReq;
import com.team404x.greenplate.company.model.response.CompanyDetailsRes;
import com.team404x.greenplate.company.repository.CompanyRepository;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import com.team404x.greenplate.utils.jwt.JwtUtil;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
	private final CompanyRepository companyRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public void signup(CompanySignupReq companySignupReq) throws Exception {
		Company company = Company.builder()
			.email(companySignupReq.getEmail())
			.password(passwordEncoder.encode(companySignupReq.getPassword()))
			.comNum(companySignupReq.getComNum())
			.role(GlobalMessage.ROLE_COMPANY.getMessage())
			.name(companySignupReq.getName())
			.address(companySignupReq.getAddress())
			.telNum(companySignupReq.getTelNum())
			.build();

		companyRepository.save(company);
	}

	public Cookie login(CompanyLoginReq companyLoginReq) throws Exception {
		String email = companyLoginReq.getEmail() + GlobalMessage.COMPANY_SUFFIX.getMessage();
		String password = companyLoginReq.getPassword();

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
		Authentication authentication = authenticationManager.authenticate(authToken);
		if (authentication != null) {
			CustomUserDetails companyDetails = (CustomUserDetails) authentication.getPrincipal();
			var role = authentication.getAuthorities().iterator().next().getAuthority();
			String token = jwtUtil.createToken(companyDetails.getId(), email, role);
			return jwtUtil.createCookie(token);
		}
		return null;
	}

	public CompanyDetailsRes details(String email) throws Exception {
		Company company = companyRepository.findCompanyByEmail(email);
		return CompanyDetailsRes.builder()
			.email(company.getEmail())
			.comNum(company.getComNum())
			.name(company.getName())
			.address(company.getAddress())
			.telNum(company.getTelNum())
			.build();
	}
	public boolean duplicateEmail(String email) {
		return companyRepository.findByEmail(email).isPresent();
	}
}
