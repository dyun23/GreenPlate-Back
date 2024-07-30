package com.team404x.greenplate.company.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.common.GlobalMessage;
import com.team404x.greenplate.company.model.request.CompanyLoginReq;
import com.team404x.greenplate.company.model.request.CompanySignupReq;
import com.team404x.greenplate.company.model.response.CompanyDetailsRes;
import com.team404x.greenplate.company.service.CompanyService;
import com.team404x.greenplate.config.SecuredOperation;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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

	@Operation(summary = "[전체] 사업자 회원가입 API")
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public BaseResponse signup(@RequestBody CompanySignupReq companySignupReq) {
		try {
			companyService.signup(companySignupReq);
			return new BaseResponse(BaseResponseMessage.COMPANY_SIGNUP_SUCCESS);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.COMPANY_SIGNUP_FAIL_PAYLOAD_INVALID);
		}
	}

	@Operation(summary = "[전체] 사업자 로그인 API")
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public BaseResponse login(@Valid @RequestBody CompanyLoginReq companyLoginReq, HttpServletResponse response) {
		try {
			Cookie jwtCookie = companyService.login(companyLoginReq);
			response.addCookie(jwtCookie);
			return new BaseResponse(BaseResponseMessage.COMPANY_LOGIN_SUCCESS);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.COMPANY_LOGIN_FAIL);
		}
	}

	@SecuredOperation
	@Operation(summary = "[사업자] 사업자 상세정보 API")
	@RequestMapping(method = RequestMethod.GET, value = "/details")
	public BaseResponse details(@AuthenticationPrincipal CustomUserDetails company) {
		try {
			String email = company.getUsername().split(GlobalMessage.COMPANY_SUFFIX.getMessage())[0];
			CompanyDetailsRes companyDetailsRes = companyService.details(email);
			return new BaseResponse(BaseResponseMessage.COMPANY_DETAILS_SUCCESS, companyDetailsRes);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.COMPANY_DETAILS_FAIL);
		}
	}
}
