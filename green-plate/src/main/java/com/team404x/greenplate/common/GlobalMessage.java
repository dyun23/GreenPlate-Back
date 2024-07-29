package com.team404x.greenplate.common;

public enum GlobalMessage {
	// 로그인 권한 관련 메세지
	USER_SUFFIX("_user"),
	ROLE_USER("ROLE_USER"),
	COMPANY_SUFFIX("_company"),
	ROLE_COMPANY("ROLE_COMPANY"),
	ACCESS_TOKEN("ATOKEN"),
	// AUTHORIZATION_HEADER("Authorization"),
	// AUTHORIZATION_VALUE("Bearer "),

	// 이메일 인증 관련 메세지
	EMAIL_TITLE("[Green Plate] 인증 메세지"),
	EMAIL_URL_TEMPLATE("http://localhost:8080/email/verify?email=%s&uuid=%s&role=%s"),
	EMAIL_ROLE_USER("user"),

	JWT_EMAIL("email"),
	JWT_ROLE("role"),
	JWT_ID("id"),
	;

	private final String message;

	GlobalMessage(String message) {
		this.message = message;
	}


	public String getMessage() {
		return message;
	}

	public String formatUrl(String email, String uuid, String role) {
		return String.format(message, email, uuid, role);
	}
}
