package com.team404x.greenplate.orders.controller;

import static com.team404x.greenplate.common.BaseResponseMessage.*;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.config.SecuredOperation;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import com.team404x.greenplate.orders.model.entity.OrdersQueryProjection;
import com.team404x.greenplate.orders.model.requset.*;
import com.team404x.greenplate.orders.model.response.OrderPaymentRes;
import com.team404x.greenplate.orders.model.response.OrderUserSearchDetailRes;
import com.team404x.greenplate.orders.model.response.OrderUserSearchRes;
import com.team404x.greenplate.orders.service.OrdersService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
public class OrdersController {
    private final OrdersService ordersService;

    @SecuredOperation
    @Operation(summary = "[유저] 상품 결제 방법 선택 API")
    @PostMapping(value = "/payment")
    public BaseResponse<OrderPaymentRes> chosePayment(@RequestBody OrderCreateReq orderCreateReq) {
        BaseResponse<OrderPaymentRes> result = ordersService.chosePayment(orderCreateReq);
        return result;
    }

    @SecuredOperation
    @Operation(summary = "[유저] 상품 결제 API")
    @PostMapping(value = "/create")
    public BaseResponse create(@AuthenticationPrincipal CustomUserDetails user,
        @RequestBody OrderCreateReq orderCreateReq) {
        try {
            BaseResponse<String> result = ordersService.createOrder(orderCreateReq);
            return result;
        } catch (Exception e) {
            return new BaseResponse<>(ORDERS_CREATED_FAIL);
        }
    }

    @SecuredOperation
    @Operation(summary = "[유저] 상품 취소 API")
    @PutMapping(value = "/cancel")
    public BaseResponse cancel(@RequestBody OrderCancelReq orderCancelReq) {
        BaseResponse result = ordersService.cancelOrder(orderCancelReq);
        return result;
    }


    @SecuredOperation
    @Operation(summary = "[유저] 주문 목록 조회 API")
    @GetMapping("/list/user")
    public BaseResponse<List<OrderUserSearchRes>> searchForUser(@AuthenticationPrincipal CustomUserDetails user) {
        try {
            BaseResponse<List<OrderUserSearchRes>> result = ordersService.searchForUser(user.getId());
            return result;
        } catch (Exception e) {
            return new BaseResponse<>(ORDERS_SEARCH_FAIL_USER);
        }
    }

    @SecuredOperation
    @Operation(summary = "[유저] 주문 상세 내역 조회 API")
    @GetMapping("/list/user/{ordersId}")
    public BaseResponse<List<OrderUserSearchDetailRes>> searchForUserDetail(@AuthenticationPrincipal CustomUserDetails user,
        @PathVariable Long ordersId) {
        try {
            BaseResponse<List<OrderUserSearchDetailRes>> result = ordersService.searchForUserDetail(user.getId(), ordersId);
            return result;
        } catch (Exception e) {
            return new BaseResponse<>(ORDERS_SEARCH_FAIL_USER);
        }
    }

    @SecuredOperation
//    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "[사업자] 주문 내역 조회 API")
    @GetMapping("/list/company")
    public BaseResponse<List<OrdersQueryProjection>> searchForCompany(@AuthenticationPrincipal CustomUserDetails company,
        OrderSearchReq search) {
        try {
            BaseResponse<List<OrdersQueryProjection>> result = ordersService.searchForCompany(company.getId(), search);
            return result;
        } catch (Exception e) {
            return new BaseResponse(BaseResponseMessage.ORDERS_SEARCH_FAIL_ORDERED);
        }
    }

    @SecuredOperation
    @Operation(summary = "[사업자] 주문 상세내역 조회 API")
    @GetMapping("/list/company/{orderId}")
    public BaseResponse<List<OrdersQueryProjection>> searchForCompanyDetail(@AuthenticationPrincipal CustomUserDetails company,
        @PathVariable Long orderId) {
        try {
            BaseResponse<List<OrdersQueryProjection>> result = ordersService.searchForCompanyDetail(company.getId(), orderId);
            return result;
        } catch (Exception e) {
            return new BaseResponse<>(BaseResponseMessage.ORDERS_SEARCH_FAIL_ORDERED);
        }
    }

    @SecuredOperation
    @Operation(summary = "[사업자] 배송 상태 변경 API")
    @PutMapping("/statechange")
    public BaseResponse changeDeliveryState(@AuthenticationPrincipal CustomUserDetails company,
        @RequestBody OrderSearchReq orderSearchReq) {
        try {
            BaseResponse result = ordersService.changeDeliveryState(orderSearchReq);
            return company.getId() == null ? new BaseResponse<>(ORDERS_UPDATE_FAIL_CHANGE) : result;
        } catch (Exception e) {
            return new BaseResponse<>(ORDERS_UPDATE_FAIL_CHANGE);
        }
    }

    @SecuredOperation
    @Operation(summary = "[사업자] 송장 번호 등록 API")
    @PostMapping(value = "/invoice")
    public BaseResponse inputInvoice(@AuthenticationPrincipal CustomUserDetails company,
        @RequestBody OrderInvoiceReq orderInvoiceReq) {
        try {
            BaseResponse result = ordersService.inputInvoice(orderInvoiceReq);
            return company.getId() == null ? new BaseResponse<>(ORDERS_SEARCH_FAIL_ORDERED) : result;
        } catch (Exception e) {
            return new BaseResponse<>(ORDERS_SEARCH_FAIL_ORDERED);
        }
    }


    @SecuredOperation
    @Operation(summary = "[유저] 카카오 페이 결제 API")
    @PostMapping(value = "/kakaoPay")
    public  ResponseEntity<String> kakaoPay(@RequestBody OrderCreateReq orderCreateReq) throws Exception {
        try{
            IamportResponse<Payment> info = ordersService.getPaymentInfo(orderCreateReq.getImpUid());
            System.out.println(orderCreateReq.getImpUid());
            Boolean payCheck = ordersService.payCheck(orderCreateReq,info);
            if(payCheck) {
                System.out.println("ok");
                BaseResponse<String> resultPay = ordersService.createOrder(orderCreateReq);
                return ResponseEntity.ok("ok");
            }
            else {
                System.out.println("error");
                ordersService.refund(orderCreateReq, info);
                return ResponseEntity.ok("error");
            }
        } catch (Exception e) {
            return ResponseEntity.ok("error");
        }
    }

    @PostMapping(value = "/kakaoRefund")
    public  BaseResponse kakaoRefund(@RequestBody OrderCancelReq orderCancelReq) throws IamportResponseException, IOException {
        try{
            BaseResponse result = ordersService.kakaoPayRefund(orderCancelReq);
            return result;
        } catch (Exception e) {
            return new BaseResponse<>(ORDERS_CANCEL_FAIL_KAKAO);
        }
    }
}
