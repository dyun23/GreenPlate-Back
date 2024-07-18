package com.team404x.greenplate.company.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.company.model.request.CompanyLoginReq;
import com.team404x.greenplate.company.model.request.CompanySignupReq;
import com.team404x.greenplate.company.model.response.CompanyDetailsRes;
import com.team404x.greenplate.company.service.CompanyService;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;

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
			return new BaseResponse(BaseResponseMessage.COMPANY_SIGNUP_SUCCESS);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.COMPANY_SIGNUP_FAIL_PAYLOAD_INVALID);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public BaseResponse login(@RequestBody CompanyLoginReq companyLoginReq, HttpServletResponse response) {
		try {
			String jwtToken = companyService.login(companyLoginReq);
			response.setHeader("Authorization", "Bearer " + jwtToken);
			return new BaseResponse(BaseResponseMessage.COMPANY_LOGIN_SUCCESS);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.COMPANY_LOGIN_FAIL);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/details")
	public BaseResponse details(@AuthenticationPrincipal CustomUserDetails company) {
		try {
			String email = company.getUsername().split("_company")[0];
			CompanyDetailsRes companyDetailsRes = companyService.details(email);
			return new BaseResponse(BaseResponseMessage.COMPANY_DETAILS_SUCCESS, companyDetailsRes);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.COMPANY_DETAILS_FAIL);
		}
	}
}
