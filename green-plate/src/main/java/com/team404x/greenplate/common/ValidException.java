package com.team404x.greenplate.common;

import java.util.ArrayList;


import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.team404x.greenplate.company.model.request.CompanyLoginReq;
import com.team404x.greenplate.company.model.request.CompanySignupReq;
import com.team404x.greenplate.user.model.request.UserLoginReq;
import com.team404x.greenplate.user.model.request.UserSignupReq;

@ControllerAdvice
@RestController
public class ValidException {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public BaseResponse processValidationError(MethodArgumentNotValidException exception) {
		BindingResult bindingResult = exception.getBindingResult();
		ArrayList<BaseResponse> errorMessages = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errorMessages.add(getError(fieldError));
		}
		return new BaseResponse(BaseResponseMessage.REQUEST_FAIL, errorMessages);
	}

	private BaseResponse getError(FieldError fieldError) {
		String objName = fieldError.getObjectName().toLowerCase();
		String error = fieldError.getField();

		if (objName.equals(UserSignupReq.class.getSimpleName().toLowerCase())) {
			return switch (error) {
				case "email" -> new BaseResponse(BaseResponseMessage.USER_SIGNUP_FAIL_EMAIL);
				case "password" -> new BaseResponse(BaseResponseMessage.USER_SIGNUP_FAIL_PASSWORD);
				case "name" -> new BaseResponse(BaseResponseMessage.USER_SIGNUP_FAIL_NAME);
				case "nickname" -> new BaseResponse(BaseResponseMessage.USER_SIGNUP_FAIL_NICKNAME);
				case "birthday" -> new BaseResponse(BaseResponseMessage.USER_SIGNUP_FAIL_BIRTHDAY);
				default -> new BaseResponse(BaseResponseMessage.USER_SIGNUP_FAIL_PAYLOAD_INVALID);
			};
		} else if (objName.equals(UserLoginReq.class.getSimpleName().toLowerCase())) {
			return switch (error) {
				case "email" -> new BaseResponse(BaseResponseMessage.USER_LOGIN_FAIL_EMAIL);
				case "password" -> new BaseResponse(BaseResponseMessage.USER_LOGIN_FAIL_PASSWORD);
				default -> new BaseResponse(BaseResponseMessage.USER_LOGIN_FAIL);
			};
		} else if (objName.equals(CompanySignupReq.class.getSimpleName().toLowerCase())) {
			return switch (error) {
				case "email" -> new BaseResponse(BaseResponseMessage.COMPANY_SIGNUP_FAIL_EMAIL);
				case "password" -> new BaseResponse(BaseResponseMessage.COMPANY_SIGNUP_FAIL_PASSWORD);
				case "comNum" -> new BaseResponse(BaseResponseMessage.COMPANY_SIGNUP_FAIL_COMNUM);
				case "name" -> new BaseResponse(BaseResponseMessage.COMPANY_SIGNUP_FAIL_NAME);
				case "address" -> new BaseResponse(BaseResponseMessage.COMPANY_SIGNUP_FAIL_ADDRESS);
				case "telNum" -> new BaseResponse(BaseResponseMessage.COMPANY_SIGNUP_FAIL_TELNUM);
				default -> new BaseResponse(BaseResponseMessage.COMPANY_SIGNUP_FAIL_PAYLOAD_INVALID);
			};
		} else if (objName.equals(CompanyLoginReq.class.getSimpleName().toLowerCase())) {
			return switch (error) {
				case "email" -> new BaseResponse(BaseResponseMessage.COMPANY_LOGIN_FAIL_EMAIL);
				case "password" -> new BaseResponse(BaseResponseMessage.COMPANY_LOGIN_FAIL_PASSWORD);
				default -> new BaseResponse(BaseResponseMessage.COMPANY_LOGIN_FAIL);
			};
		}
		return null;
	}
}
