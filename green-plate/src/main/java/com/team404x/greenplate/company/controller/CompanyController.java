package com.team404x.greenplate.company.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.company.model.request.CompanyLoginReq;
import com.team404x.greenplate.company.model.request.CompanySignupReq;
import com.team404x.greenplate.company.service.CompanyService;
import com.team404x.greenplate.user.model.request.UserLoginReq;
import com.team404x.greenplate.user.model.request.UserSignupReq;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
	private final CompanyService companyService;

	/*
	TODO
	company 이메일 인증 어떻게 할 지 정하기
	* */
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public BaseResponse signup(@RequestBody CompanySignupReq companySignupReq) {
		try {
			companyService.signup(companySignupReq);
			return new BaseResponse<>(BaseResponseMessage.COMPANY_SIGNUP_SUCCESS);
		} catch (Exception e) {
			return new BaseResponse(null);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public BaseResponse login(@RequestBody CompanyLoginReq companyLoginReq, HttpServletResponse response) {
		String jwtToken = companyService.login(companyLoginReq);
		if (jwtToken != null) {
			response.setHeader("Authorization", "Bearer " + jwtToken);
			return new BaseResponse(BaseResponseMessage.COMPANY_LOGIN_SUCCESS);
		}
		return new BaseResponse(null);
	}
}