package com.team404x.greenplate.company.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.company.model.request.CompanyLoginReq;
import com.team404x.greenplate.company.model.request.CompanySignupReq;
import com.team404x.greenplate.company.repository.CompanyRepository;
import com.team404x.greenplate.utils.jwt.JwtUtil;

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
			.role("ROLE_COMPANY")
			.name(companySignupReq.getName())
			.address(companySignupReq.getAddress())
			.telNum(companySignupReq.getTelNum())
			.build();

		companyRepository.save(company);
	}

	public String login(CompanyLoginReq companyLoginReq) {
		String email = companyLoginReq.getEmail() + "_company";
		String password = companyLoginReq.getPassword();

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
		Authentication authentication = authenticationManager.authenticate(authToken);
		if (authentication != null) {
			var role = authentication.getAuthorities().iterator().next().getAuthority();
			String token = jwtUtil.createToken(email, role);
			System.out.println(token);
			return token;
		}
		return null;
	}
}