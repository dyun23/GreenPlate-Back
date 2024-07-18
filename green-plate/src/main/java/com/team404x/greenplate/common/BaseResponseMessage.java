package com.team404x.greenplate.common;

public enum BaseResponseMessage {

	COMPANY_SIGNUP_SUCCESS(true, 1000, "정상적으로 회원가입되었습니다."),
	COMPANY_LOGIN_SUCCESS(true, 1001, "정상적으로 로그인되었습니다."),
	USER_SIGNUP_SUCCESS(true, 1010, "정상적으로 회원가입 되었습니다."),
	USER_SIGNUP_FAIL_PASSWORD(false, 1200, "올바르게 비밀번호를 입력하세요."),
	USER_SIGNUP_FAIL_EMPTY_NICKNAME(false, 1201, "닉네임을 입력해주세요."),
	USER_SIGNUP_FAIL_EMPTY_PASSWORD(false, 1202, "패스워드를 입력해주세요."),
	USER_SIGNUP_FAIL_EMPTY_KEYWORD(false, 1203,"키워드를 입력해주세요."),
	USER_SIGNUP_FAIL_EXISTING_EMAIL(false, 1204, "이미 존재하는 이메일 주소입니다."),
	USER_SIGNUP_FAIL_NOT_CONFIRMED_CODE(false, 1205, "인증번호를 확인해주세요."),
	USER_SIGNUP_FAIL_EXPIRED_CODE(false, 1206, "만료된 인증번호입니다."),
	USER_SIGNUP_FAIL_PAYLOAD_INVALID(false, 1207,"입력값을 확인해주세요."),
	USER_LOGIN_SUCCESS(true, 1070, "정상적으로 로그인되었습니다."),
	USER_LOGIN_FAIL_EMAIL(false, 1800, "이메일을 정확히 입력하세요."),
//	5000 orders
	ORDERS_CREATED_SUCCESS(true, 5100, "주문이 완료되었습니다."),
	ORDERS_CANCEL_SUCCESS(true, 5110, "주문이 취소되었습니다."),
	ORDERS_USER_FAIL_LIST(false, 5001, "주문 내역 조회에 실패하였습니다."),
	ORDERS_SEARCH_FAIL_USER(false, 5052, "유저가 없습니다."),
	ORDERS_UPDATE_SUCCESS_CHANGE(false, 5070, "주문 상태가 변경되었습니다."),
	ORDERS_UPDATE_FAIL_CHANGE(false, 5071, "주문 상태 변경에 실패하였습니다."),
	ORDERS_UPDATE_SUCCESS_INVOICE(false, 5080, "송장 입력이 완료되었습니다."),
	ORDERS_UPDATE_FAIL_INVOICE(false, 5081, "송장 입력에 실패하였습니다."),
	ORDERS_CREATED_FAIL(false, 5401, "주문 실패하였습니다."),
	ORDERS_CREATED_FAIL_STOCK(false, 5401, "상품재고가 부족합니다."),
	ORDERS_SEARCH_FAIL_ORDERED(false, 5111, "해당주문을 찾을 수 없습니다.");


	private Boolean success;
	private Integer code;
	private String message;

	BaseResponseMessage(Boolean success, Integer code, String message) {
		this.success = success;
		this.code = code;
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}


}
