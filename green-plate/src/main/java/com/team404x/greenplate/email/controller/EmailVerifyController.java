package com.team404x.greenplate.email.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.common.GlobalMessage;
import com.team404x.greenplate.email.service.EmailVerifyService;
import com.team404x.greenplate.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/email")
@RequiredArgsConstructor
public class EmailVerifyController {
	private final EmailVerifyService emailVerifyService;
	private final UserService userService;

	@Operation(summary = "[전체] 이메일 유효 검증용 API 입니다")
	@RequestMapping(method = RequestMethod.GET, value = "/verify")
	public BaseResponse verify(String email, String uuid, String role) {
		BaseResponseMessage message = BaseResponseMessage.EMAIL_VERIFY_FAIL;
		try {
			if (emailVerifyService.isExistEmail(email, uuid)) {
				if (role.equals(GlobalMessage.EMAIL_ROLE_USER.getMessage())) {
					userService.activateUser(email);
					message = BaseResponseMessage.EMAIL_VERIFY_SUCCESS;
				}
			}
			return new BaseResponse(message);
		} catch (Exception e) {
			return new BaseResponse(message);
		}
	}
}
