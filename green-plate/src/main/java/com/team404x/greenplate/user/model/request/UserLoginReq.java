package com.team404x.greenplate.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginReq {
	@Email
	String email;
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$")
	String password;
}
