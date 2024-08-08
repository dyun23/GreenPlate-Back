package com.team404x.greenplate.user.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.common.GlobalMessage;
import com.team404x.greenplate.config.SecuredOperation;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import com.team404x.greenplate.email.service.EmailVerifyService;
import com.team404x.greenplate.user.model.request.UserAddressRegisterReq;
import com.team404x.greenplate.user.model.request.UserLoginReq;
import com.team404x.greenplate.user.model.request.UserSignupReq;
import com.team404x.greenplate.user.model.response.UserDetailsRes;
import com.team404x.greenplate.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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

	@Operation(summary = "[전체] 유저 회원가입 API")
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public BaseResponse signup(@Valid @RequestBody UserSignupReq userSignupReq) {
		try {
			emailVerifyService.saveEmail(userSignupReq.getEmail(), GlobalMessage.EMAIL_ROLE_USER.getMessage());
			userService.signup(userSignupReq);
			return new BaseResponse<>(BaseResponseMessage.USER_SIGNUP_SUCCESS);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.USER_SIGNUP_FAIL_PAYLOAD_INVALID);
		}
	}

	@Operation(summary = "[전체] 유저 로그인 API")
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public BaseResponse login(@Valid @RequestBody UserLoginReq userLoginReq, HttpServletResponse response) {
		try {
			Cookie jwtCookie = userService.login(userLoginReq);
			response.addCookie(jwtCookie);
			return new BaseResponse(BaseResponseMessage.USER_LOGIN_SUCCESS);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.USER_LOGIN_FAIL);
		}
	}

	@SecuredOperation
	@Operation(summary = "[유저] 유저 상세 정보 조회 API")
	@RequestMapping(method = RequestMethod.GET, value = "/details")
	public BaseResponse details(@AuthenticationPrincipal CustomUserDetails user) {
		try {
			String email = user.getUsername().split(GlobalMessage.USER_SUFFIX.getMessage())[0];
			UserDetailsRes userDetailsRes = userService.details(email);
			return new BaseResponse(BaseResponseMessage.USER_DETAILS_SUCCESS, userDetailsRes);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.USER_DETAILS_FAIL);
		}
	}

	@SecuredOperation
	@Operation(summary = "[유저] 유저 배송지 등록 API")
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

	@SecuredOperation
	@Operation(summary = "[유저] 디폴트 배송지 설정 API")
	@RequestMapping(method = RequestMethod.GET, value = "/address/default")
	public BaseResponse updateDefaultAddress(@AuthenticationPrincipal CustomUserDetails user, Long id) {
		try {
			userService.updateDefaultAddress(user.getId(), id);
			return new BaseResponse(BaseResponseMessage.USER_ADDRESS_REGISTER_SUCCESS);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.USER_ADDRESS_REGISTER_FAIL);
		}
	}

	@Operation(summary = "[유저] 키워드 불러오기 API")
	@RequestMapping(method = RequestMethod.GET, value = "/keyword/list")
	public BaseResponse getUserKeyword(@AuthenticationPrincipal CustomUserDetails user) {
		try {
			List<String> keywords = userService.getUserKeyword(user.getId());
			return new BaseResponse(BaseResponseMessage.REQUEST_SUCCESS, keywords);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.REQUEST_FAIL);
		}
	}

	@Operation(summary = "[유저] 키워드 저장 API")
	@RequestMapping(method = RequestMethod.GET, value = "/create")
	public BaseResponse createUserKeyword(@AuthenticationPrincipal CustomUserDetails user, String keyword) {
		try {
			String[] keywords = keyword.split(",");
			userService.createUserKeyword(user.getId(), keywords);
			return new BaseResponse(BaseResponseMessage.REQUEST_SUCCESS);
		} catch (Exception e) {
			return new BaseResponse(BaseResponseMessage.REQUEST_FAIL);
		}
	}

}
