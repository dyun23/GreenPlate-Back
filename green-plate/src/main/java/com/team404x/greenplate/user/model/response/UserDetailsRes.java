package com.team404x.greenplate.user.model.response;

import java.time.LocalDate;
import java.util.List;

import com.team404x.greenplate.user.address.entity.Address;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDetailsRes {
	String email;
	String name;
	String nickname;
	LocalDate birthday;
	List<UserDetailsAddressRes> addresses;
}
