package com.team404x.greenplate.common;


public enum BaseResponseMessage {
	// company signup
	COMPANY_SIGNUP_SUCCESS(true, 1000, "정상적으로 회원가입되었습니다."),
	COMPANY_SIGNUP_FAIL_PASSWORD(false, 1001, "올바르게 비밀번호를 입력하세요."),
	COMPANY_SIGNUP_FAIL_EMPTY_NICKNAME(false, 1002, "닉네임을 입력해주세요."),
	COMPANY_SIGNUP_FAIL_EMPTY_PASSWORD(false, 1003, "패스워드를 입력해주세요."),
	COMPANY_SIGNUP_FAIL_EXISTING_EMAIL(false, 1004, "이미 존재하는 이메일 주소입니다."),
	COMPANY_SIGNUP_FAIL_NOT_CONFIRMED_CODE(false, 1005, "인증번호를 확인해주세요."),
	COMPANY_SIGNUP_FAIL_EXPIRED_CODE(false, 1006, "만료된 인증번호입니다."),
	COMPANY_SIGNUP_FAIL_PAYLOAD_INVALID(false, 1007,"입력값을 확인해주세요."),
	// company login
	COMPANY_LOGIN_SUCCESS(true, 1050, "정상적으로 로그인되었습니다."),
	COMPANY_LOGIN_FAIL(false, 1051, "로그인에 실패했습니다."),
	COMPANY_LOGIN_FAIL_EMAIL(false, 1052, "이메일을 정확히 입력하세요."),
	COMPANY_LOGIN_FAIL_PASSWORD(false, 1053, "비밀번호를 정확히 입력하세요."),

	COMPANY_DETAILS_SUCCESS(true, 1100, "상세 정보를 성공적으로 불러왔습니다"),
	COMPANY_DETAILS_FAIL(false, 1100, "상세 정보를 불러오는데 실패했습니다"),

	// user signup
	USER_SIGNUP_SUCCESS(true, 1010, "정상적으로 회원가입 되었습니다."),
	USER_SIGNUP_FAIL_PASSWORD(false, 1200, "올바르게 비밀번호를 입력하세요."),
	USER_SIGNUP_FAIL_EMPTY_NICKNAME(false, 1201, "닉네임을 입력해주세요."),
	USER_SIGNUP_FAIL_EMPTY_PASSWORD(false, 1202, "패스워드를 입력해주세요."),
	USER_SIGNUP_FAIL_EMPTY_KEYWORD(false, 1203,"키워드를 입력해주세요."),
	USER_SIGNUP_FAIL_EXISTING_EMAIL(false, 1204, "이미 존재하는 이메일 주소입니다."),
	USER_SIGNUP_FAIL_NOT_CONFIRMED_CODE(false, 1205, "인증번호를 확인해주세요."),
	USER_SIGNUP_FAIL_EXPIRED_CODE(false, 1206, "만료된 인증번호입니다."),
	USER_SIGNUP_FAIL_PAYLOAD_INVALID(false, 1207,"입력값을 확인해주세요."),

	// user login
	USER_LOGIN_SUCCESS(true, 1070, "정상적으로 로그인되었습니다."),
	USER_LOGIN_FAIL(false, 1300, "로그인에 실패했습니다."),
	USER_LOGIN_FAIL_EMAIL(false, 1301, "이메일을 정확히 입력하세요."),
	USER_LOGIN_FAIL_PASSWORD(false, 1302, "비밀번호를 정확히 입력하세요."),

	// user details
	USER_DETAILS_SUCCESS(true, 1400, "유저 상세 정보를 성공적으로 불러왔습니다"),
	USER_DETAILS_FAIL(true, 1401, "유저 상세 정보를 불러오는데 실패했습니다"),

	// user email verify
	EMAIL_VERIFY_SUCCESS(true, 1500, "이메일 인증에 성공했습니다."),
	EMAIL_VERIFY_FAIL(false, 1550, "이메일 인증에 실패했습니다."),

	// user address
	USER_ADDRESS_REGISTER_SUCCESS(true, 1600, "주소 등록에 성공했습니다."),
	USER_ADDRESS_REGISTER_FAIL(false, 1650, "주소 등록에 실패했습니다"),


	// item 조회
	ITEM_LIST_SUCCESS(true, 3000, "전체 상품 목록을 성공적으로 불러왔습니다"),
	ITEM_LIST_FAIL_EMPTY(false, 3001, "상품 목록이 없습니다"),
	ITEM_LIST_FAIL(false, 3002, "상품을 불러오는데 실패하였습니다"),

	ITEM_LIST_CATEGORY_SUCCESS(true, 3040, "해당 카테고리 상품 목록을 성공적으로 불러왔습니다"),
	ITEM_LIST_CATEGORY_FAIL(false, 3041, "해당 카테고리 상품 목록을 불러오는데 실패하였습니다"),
	ITEM_LIST_CATEGORY_FAIL_EMPTY_MAIN(false, 3042, "상위 카테고리가 없습니다"),
	ITEM_LIST_CATEGORY_FAIL_EMPTY_SUB(false, 3043, "하위 카테고리가 없습니다"),

	ITEM_LIST_NAME_SUCCESS(true, 3080, "상품 검색을 성공했습니다"),
	ITEM_LIST_NAME_FAIL(false, 3081, "상품 검색을 실패했습니다"),
	ITEM_LIST_NAME_FAIL_NOT_FOUND(false, 3082, "검색에 맞는 상품이 없습니다"),

	ITEM_DETAILS_SUCCESS(true, 3100, "선택한 상품의 정보를 성공적으로 불러왔습니다"),
	ITEM_DETAILS_FAIL(false, 3101, "상품 상세정보를 불러오는데 실패하였습니다"),
	ITEM_DETAILS_FAIL_SOLD_OUT(false, 3102, "품절된 상품입니다"),
	ITEM_DETAILS_FAIL_UNAVAILABLE(false, 3103, "판매 중지된 상품입니다"),
	ITEM_DETAILS_FAIL_NOT_FOUND(false, 3104, "존재하지 않는 상품입니다"),



//	5000 orders
	ORDERS_CREATED_SUCCESS(true, 5100, "주문이 완료되었습니다."),
	ORDERS_CANCEL_SUCCESS(true, 5110, "주문이 취소되었습니다."),
	ORDERS_USER_SUCCESS_LIST(true, 5000, "주문 내역 조회에 성공하였습니다."),
	ORDERS_USER_FAIL_LIST(false, 5001, "주문 내역 조회에 실패하였습니다."),

	ORDERS_SEARCH_SUCCESS_USER(true, 5051, "주문 상세 조회에 성공하였습니다."),
	ORDERS_SEARCH_FAIL_USER(false, 5052, "유저가 없습니다."),
	ORDERS_UPDATE_SUCCESS_CHANGE(false, 5070, "주문 상태가 변경되었습니다."),
	ORDERS_UPDATE_FAIL_CHANGE(false, 5071, "주문 상태 변경에 실패하였습니다."),
	ORDERS_UPDATE_SUCCESS_INVOICE(false, 5080, "송장 입력이 완료되었습니다."),
	ORDERS_UPDATE_FAIL_INVOICE(false, 5081, "송장 입력에 실패하였습니다."),
	ORDERS_CREATED_FAIL(false, 5401, "결제 실패하였습니다."),
	ORDERS_CREATED_FAIL_STOCK(false, 5401, "상품재고가 부족합니다."),
	ORDERS_SEARCH_FAIL_ORDERED(false, 5111, "해당주문을 찾을 수 없습니다."),
	ORDERS_CANCEL_SUCCESS_KAKAO(false, 5500, "카카오페이 결제 취소되었습니다."),
	ORDERS_CANCEL_FAIL_KAKAO(false, 5501, "카카오페이 결제 취소 실패하였습니다."),
	ORDERS_CREATED_FAIL_CHECK(false, 5501, "주문 상품가격과 결제금액이 일치하지 않아 결제취소됩니다"),
	ORDERS_CREATED_SUCCESS_KAKAO(false, 5600, "카카오페이 결제 성공하였습니다."),
	ORDERS_CREATED_FAIL_ITEM(false, 5601, "결제대상 상품 정보를 찾을 수 없습니다."),
	ORDERS_CANCEL_FAIL_ALREADY(false, 5611, "이미 환불완료된 주문입니다"),
	ORDERS_CANCEL_FAIL_NONIMPUID(false, 5612, "ImpUid 정보가 없습니다. 결제정보를 확인해 주세요"),

	USER_CREATE_SUCCESS(true, 3200, "상품 등록이 완료되었습니다"),
	USER_CREATE_FAIL(false,3201,"상품을 등록하는데 실패했습니다"),
	USER_CREATE_FAIL_ISEMPTY(false,3202,"비워져있는 값이 있습니다"),
	USER_CREATE_FAIL_PRICE(false,3203,"할인 금액이 정가보다 큽니다"),
	USER_CREATE_FAIL_QUANTITYANDPRICE(false, 3204,"수량이나 금액이 0이하입니다"),

	USER_UPDATE_SUCCESS(true,3220,"상품 수정이 완료되었습니다"),
	USER_UPDATE_FAIL(false,3221,"상품을 등록하는데 실패했습니다"),
	USER_UPDATE_FAIL_ISEMPTY(false,3223,"비워져 있는 값이 있습니다"),
	USER_UPDATE_FAIL_PRICE(false,3224,"할인금액이 정가보다 큽니다"),
	USER_UPDATE_FAIL_QUANTITYANDPRICE(false,3225,"수량이나 금액이 0 이하입니다"),
	USER_UPDATE_FAIL_NULL(false,3226,"존재하지 않는 상품입니다"),

	USER_DELETE_SUCCESS(true,3260,"상품 삭제에 성공하였습니다"),
	USER_DELETE_FAIL(false,3261,"상품을 삭제하는데 실패했습니다"),
	USER_DELETE_FAIL_NOTSELLER(false,3262,"해당 판매자의 상품이 아닙니다"),
	USER_DELETE_FAIL_NULL(false,3263,"상품이 존재하지 않습니다"),

	USER_READ_LIST_SUCCESS(true,3280,"상품 검색에 성공했습니다"),
	USER_READ_LIST_FAIL(false,3281,"상품을 불러오는데 실패하였습니다"),
	USER_READ_LIST_FAIL_NULL(false,3283,"해당 판매자의 상품이 없습니다"),

	USER_READ_DETAIL_SUCCESS(true,3300,"상품 상세 정보를 성공적으로 불러왔습니다"),
	USER_READ_DETAIL_FAIL(false,3301,"상품 상세 정보를 불러오는데 실패하였습니다"),
	USER_READ_DETAIL_FAIL_NOTSELLER(false,3303,"해당 판매자의 상품이 아닙니다"),
	USER_READ_DETAIL_FAIL_NULL(false,3304,"해당 상품이 존재하지 않습니다"),

	USER_SEARCH_SUCCESS(true,3320,"상품 검색에 성공했습니다"),
	USER_SEARCH_FAIL(false,3321,"상품을 불러오는데 실패하였습니다"),
	USER_SEARCH_FAIL_ATLEAST(false,3322,"검색어를 2글자 이상 입력해주세요"),
	USER_SEARCH_FAIL_NOTFOUND(false,3323,"검색어에 맞는 상품이 없습니다"),


	// 레시피 응답
	RECIPE_CREATE_SUCCESS(true, 6000, "레시피 작성이 완료되었습니다."),
	RECIPE_CREATE_FAIL(false, 6001, "레시피 작성에 실패하였습니다."),
	RECIPE_CREATE_FAIL_HAS_NULL(false, 6002, "필수 값을 모두 입력해주세요"),

	RECIPE_UPDATE_SUCCESS(true, 6020, "레시피 수정이 완료되었습니다"),
	RECIPE_UPDATE_FAIL(false, 6021, "레시피 수정에 실패하였습니다"),
	RECIPE_UPDATE_FAIL_NOT_MEMBER(false, 6022, "본인이 작성한 레시피만 수정할 수 있습니다"),
	RECIPE_UPDATE_FAIL_HAS_NULL(false, 6023, "필수 값을 모두 입력해주세요"),

	RECIPE_LIST_SUCCESS(true,6040, "레시피 목록을 불러오는데 성공했습니다"),
	RECIPE_LIST_FAIL(false, 6041, "레시피 목록을 불러오는데 실패했습니다"),

	RECIPE_DETAILS_SUCCESS(true, 6060, "레시피 상세 정보를 불러오는데 성공했습니다"),
	RECIPE_DETAILS_FAIL(false, 6061, "레시피 상세 정보를 불러오는데 실패했습니다"),

	// 레시피 리뷰 응답
	RECIPE_REVIEW_CREATE_SUCCESS(true, 6100, "레시피 리뷰 등록에 성공했습니다"),
	RECIPE_REVIEW_CREATE_FAIL(false, 6101, "레시피 리뷰 등록에 실패했습니다"),

	RECIPE_REVIEW_LIST_SUCCESS(true, 6120, "레시피 리뷰 불러오기에 성공했습니다"),
	RECIPE_REVIEW_LIST_FAIL(false, 6121, "레시피 리뷰 불러오기에 실패했습니다"),

	// 장바구니 응답
	CART_ADD_SUCCESS(true, 3500, "장바구니 등록에 성공했습니다"),
	CART_ADD_FAIL(false, 3501, "장바구니 등록에 실패했습니다"),

	CART_UPDATE_SUCCESS(true, 3520, "수량 수정에 성공했습니다"),
	CART_UPDATE_FAIL(false, 3521, "수량 수정에 실패했습니다"),
	CART_UPDATE_FAIL_NULL(false, 3522, "장바구니에 해당 상품이 없습니다"),
	CART_UPDATE_FAIL_NOT_USER(false, 3522, "본인의 장바구니만 수정할 수 있습니다"),

	CART_LIST_SUCCESS(true, 3540, "장바구니에 상품을 불러오는데에 성공했습니다"),
	CART_LIST_FAIL(false, 3541, "장바구니에서 상품을 불러오는데 실패했습니다");


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
