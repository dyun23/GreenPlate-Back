package com.team404x.greenplate.company.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CompanyLoginReq {
	String email;
	String password;
}
