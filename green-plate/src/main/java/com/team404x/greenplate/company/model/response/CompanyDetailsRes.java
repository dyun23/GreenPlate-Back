package com.team404x.greenplate.company.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompanyDetailsRes {
	private String email;
	private String comNum;
	private String name;
	private String address;
	private String telNum;
}
