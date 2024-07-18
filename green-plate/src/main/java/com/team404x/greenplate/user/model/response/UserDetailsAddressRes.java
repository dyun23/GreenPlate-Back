package com.team404x.greenplate.user.model.response;

import org.hibernate.annotations.ColumnDefault;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDetailsAddressRes {
	private Integer zipcode;
	private String address;
	private String addressDetail;
}
