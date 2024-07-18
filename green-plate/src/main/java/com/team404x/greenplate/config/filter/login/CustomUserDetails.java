package com.team404x.greenplate.config.filter.login;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.user.model.entity.User;


public class CustomUserDetails implements UserDetails {
	private User user;
	private Company company;

	public CustomUserDetails(User user) {
		this.user = user;
	}
	public CustomUserDetails(Company company) {
		this.company = company;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	// 권한 설정
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				if (user == null)
					return "ROLE_COMPANY";
				return "ROLE_USER";
			}
		});
		return collection;
	}

	//
	@Override
	public String getPassword() {
		if (user == null)
			return company.getPassword();
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		if (user == null)
			return company.getEmail();
		return user.getEmail();
	}

	public Long getId() {
		if (user == null)
			return company.getId();
		return user.getId();
	}
}
