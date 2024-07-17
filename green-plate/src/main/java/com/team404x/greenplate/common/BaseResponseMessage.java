package com.team404x.greenplate.common;

public enum BaseResponseMessage {


	ORDERS_CREATED_SUCCESS(true, 5100, "주문이 완료되었습니다."),
	ORDERS_CANCEL_SUCCESS(true, 5110, "주문이 취소되었습니다."),
	ORDERS_USER_FAIL_LIST(false, 5001, "주문 내역 조회에 실패하였습니다."),
	ORDERS_SEARCH_FAIL_USER(false, 5052, "유저가 없습니다."),
	ORDERS_CREATED_FAIL(false, 5401, "주문 실패하였습니다."),
	ORDERS_CREATED_FAIL_STOCK(false, 5401, "상품재고가 부족합니다."),
	ORDERS_CANCEL_FAIL_ORDERED(false, 5111, "해당주문을 찾을 수 없습니다."),
	;

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
