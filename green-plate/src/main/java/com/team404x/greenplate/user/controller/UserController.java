package com.team404x.greenplate.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import com.team404x.greenplate.email.service.EmailVerifyService;
import com.team404x.greenplate.user.model.request.UserAddressRegisterReq;
import com.team404x.greenplate.user.model.request.UserLoginReq;
import com.team404x.greenplate.user.model.request.UserSignupReq;
import com.team404x.greenplate.user.model.response.UserDetailsRes;
import com.team404x.greenplate.user.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final EmailVerifyService emailVerifyService;
	/* TODO
	예외 로직: ControllerAdvice를 이용
	*   */
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public BaseResponse signup(@RequestBody UserSignupReq userSignupReq) {
		try {
			emailVerifyService.sendEmail(userSignupReq.getEmail(), "user");
			userService.signup(userSignupReq);
			return new BaseResponse<>(BaseResponseMessage.USER_SIGNUP_SUCCESS);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.USER_SIGNUP_FAIL_PAYLOAD_INVALID);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public BaseResponse login(@RequestBody UserLoginReq userLoginReq, HttpServletResponse response) {
		try {
			String jwtToken = userService.login(userLoginReq);
			response.setHeader("Authorization", "Bearer " + jwtToken);
			return new BaseResponse(BaseResponseMessage.USER_LOGIN_SUCCESS);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.USER_LOGIN_FAIL);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/details")
	public BaseResponse details(@AuthenticationPrincipal CustomUserDetails user) {
		try {
			String email = user.getUsername().split("_user")[0];
			UserDetailsRes userDetailsRes = userService.details(email);
			return new BaseResponse(BaseResponseMessage.USER_READ_DETAIL_SUCCESS, userDetailsRes);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.USER_READ_DETAIL_FAIL);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/address/register")
	public BaseResponse registerAddress(@AuthenticationPrincipal CustomUserDetails user,
		@RequestBody UserAddressRegisterReq userAddressRegisterReq) {
		try {
			Long id = user.getId();
			userService.registerAddress(id, userAddressRegisterReq);
			return new BaseResponse(BaseResponseMessage.USER_ADDRESS_REGISTER_SUCCESS);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.USER_ADDRESS_REGISTER_FAIL);
		}
	}
}
