package com.team404x.greenplate.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.team404x.greenplate.user.model.request.UserLoginReq;
import com.team404x.greenplate.user.model.request.UserSignupReq;
import com.team404x.greenplate.user.model.entity.User;
import com.team404x.greenplate.user.repository.UserRepository;
import com.team404x.greenplate.utils.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public void signup(UserSignupReq userSignupReq) throws Exception {
		User user = User.builder()
			.email(userSignupReq.getEmail())
			.password(passwordEncoder.encode(userSignupReq.getPassword()))
			.role("ROLE_USER")
			.name(userSignupReq.getName())
			.nickName(userSignupReq.getNickname())
			.birthday(userSignupReq.getBirthday())
			.build();

		userRepository.save(user);
	}

	public String login(UserLoginReq userLoginReq) {
		String email = userLoginReq.getEmail() + "_user";
		String password = userLoginReq.getPassword();

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
		Authentication authentication = authenticationManager.authenticate(authToken);
		if (authentication != null) {
			var role = authentication.getAuthorities().iterator().next().getAuthority();
			String token = jwtUtil.createToken(email, role);
			System.out.println(token);
			return token;
		}
		return null;
	}
}
