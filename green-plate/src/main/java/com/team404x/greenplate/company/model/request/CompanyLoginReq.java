package com.team404x.greenplate.company.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CompanyLoginReq {
	@Email
	String email;
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$")
	String password;
}
