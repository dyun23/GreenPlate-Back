package com.team404x.greenplate.email.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.email.service.EmailVerifyService;
import com.team404x.greenplate.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/email")
@RequiredArgsConstructor
public class EmailVerifyController {
	private final EmailVerifyService emailVerifyService;
	private final UserService userService;

	@RequestMapping(method = RequestMethod.GET, value = "/verify")
	public BaseResponse verify(String email, String uuid, String role) {
		if (emailVerifyService.isExist(email, uuid)) {
			if (role.equals("user")) {
				userService.activateUser(email);
				return new BaseResponse(BaseResponseMessage.USER_SIGNUP_SUCCESS);
			}
		}
		return null;
	}
}
