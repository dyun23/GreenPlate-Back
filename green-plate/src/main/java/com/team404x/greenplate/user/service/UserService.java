package com.team404x.greenplate.user.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.GlobalMessage;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import com.team404x.greenplate.user.address.entity.Address;
import com.team404x.greenplate.user.address.repository.AddressRepository;
import com.team404x.greenplate.user.model.request.UserAddressRegisterReq;
import com.team404x.greenplate.user.model.request.UserLoginReq;
import com.team404x.greenplate.user.model.request.UserSignupReq;
import com.team404x.greenplate.user.model.entity.User;
import com.team404x.greenplate.user.model.response.UserDetailsAddressRes;
import com.team404x.greenplate.user.model.response.UserDetailsRes;
import com.team404x.greenplate.user.repository.UserRepository;
import com.team404x.greenplate.utils.jwt.JwtUtil;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final AddressRepository addressRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public void signup(UserSignupReq userSignupReq) throws Exception {

		String birthYear = userSignupReq.getBirthYear();
		String birthMonth = userSignupReq.getBirthMonth();
		String birthDay = userSignupReq.getBirthday();

		String birthDateStr = birthYear + "-" + birthMonth + "-" + birthDay;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate birthdayFormat = LocalDate.parse(birthDateStr, formatter);

		User user = User.builder()
			.email(userSignupReq.getEmail())
			.password(passwordEncoder.encode(userSignupReq.getPassword()))
			.role(GlobalMessage.ROLE_USER.getMessage())
			.name(userSignupReq.getName())
			.nickName(userSignupReq.getNickname())
			.birthday(birthdayFormat)
			.build();

		userRepository.save(user);
	}

	public void activateUser(String email) throws Exception{
		User user = userRepository.findUserByEmail(email);
		user.activate();
		userRepository.save(user);
	}

	public Cookie login(UserLoginReq userLoginReq) throws Exception {
		String email = userLoginReq.getEmail() + GlobalMessage.USER_SUFFIX.getMessage();
		String password = userLoginReq.getPassword();

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
		Authentication authentication = authenticationManager.authenticate(authToken);
		if (authentication != null) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			var role = authentication.getAuthorities().iterator().next().getAuthority();
			String token = jwtUtil.createToken(userDetails.getId(), email, role);
			return jwtUtil.createCookie(token);
		}
		return null;
	}

	public UserDetailsRes details(String email) throws Exception {
		User user = userRepository.findUserByEmail(email);
		List<Address> addresses = user.getAddresses();
		List<UserDetailsAddressRes> userAddresses = new ArrayList<>();
		for (var address : addresses) {
			userAddresses.add(UserDetailsAddressRes.builder()
				.zipcode(address.getZipcode())
				.address(address.getAddress())
				.addressDetail(address.getAddressDetail())
				.build());
		}

		return UserDetailsRes.builder()
			.email(user.getEmail())
			.name(user.getName())
			.nickname(user.getNickName())
			.birthday(user.getBirthday())
			.addresses(userAddresses)
			.build();
	}

	public void registerAddress(Long id, UserAddressRegisterReq userAddressRegisterReq) throws Exception{
		Address address = Address.builder()
			.zipcode(userAddressRegisterReq.getZipcode())
			.address(userAddressRegisterReq.getAddress())
			.addressDetail(userAddressRegisterReq.getAddressDetail())
			.recipient(userAddressRegisterReq.getRecipient())
			.phoneNum(userAddressRegisterReq.getPhoneNum())
			.defultAddr(false)
			.user(User.builder().id(id).build())
			.build();

		addressRepository.save(address);
	}

	public void updateDefaultAddress(Long id, Long addressId) throws Exception {
		User user = userRepository.findById(id).orElseThrow();
		List<Address> addresses = user.getAddresses();

		for (Address address : addresses) {
			if (address.getDefultAddr() == null) continue;
			if (address.getDefultAddr()) {
				address.setDefultAddr(false);
				addressRepository.save(address);
			}
			if (address.getId().equals(addressId)) {
				address.setDefultAddr(true);
				addressRepository.save(address);
			}
		}
	}
}
