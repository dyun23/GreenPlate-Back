package com.team404x.greenplate.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserAddressRegisterReq {
	private String zipcode;
	private String address;
	private String addressDetail;
	private String recipient;
	private String phoneNum;
}
