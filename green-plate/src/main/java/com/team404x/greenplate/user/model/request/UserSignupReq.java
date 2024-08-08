package com.team404x.greenplate.user.model.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.common.GlobalMessage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserSignupReq {

	@Email
	String email;
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$")
	String password;
	@Pattern(regexp = "^[가-힣]+$")
	String name;
	@Pattern(regexp = "^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$")
	String nickname;

	String birthYear;
	String birthMonth;
	String birthday;
}
