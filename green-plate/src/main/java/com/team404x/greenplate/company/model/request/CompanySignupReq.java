package com.team404x.greenplate.company.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CompanySignupReq {
	String email;
	String password;
	String comNum;
	String name;
	String address;
	String telNum;
}
