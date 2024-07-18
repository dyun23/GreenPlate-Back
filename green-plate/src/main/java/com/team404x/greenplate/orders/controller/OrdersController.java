package com.team404x.greenplate.orders.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.orders.model.entity.OrdersQueryProjection;
import com.team404x.greenplate.orders.model.requset.*;
import com.team404x.greenplate.orders.model.response.OrderPaymentRes;
import com.team404x.greenplate.orders.model.response.OrderUserSearchDetailRes;
import com.team404x.greenplate.orders.model.response.OrderUserSearchRes;
import com.team404x.greenplate.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
public class OrdersController {
    private final OrdersService ordersService;
    private final IamportClient iamportClient;

    /** 상품 결제방법선택**/
    @PostMapping(value = "/payment")
    public BaseResponse<OrderPaymentRes> chosePayment(@RequestBody OrderCreateReq orderCreateReq) {
        BaseResponse<OrderPaymentRes> result = ordersService.chosePayment(orderCreateReq);
        return result;
    }

    /** 상품 결제**/
    @PostMapping(value = "/create")
    public BaseResponse create(@RequestBody OrderCreateReq orderCreateReq) {
        BaseResponse result = ordersService.createOrder(orderCreateReq);
        return result;
    }

    /** 주문 취소**/
    @PutMapping(value = "/cancel")
    public BaseResponse cancel(@RequestBody OrderCancelReq orderCancelReq) {
        BaseResponse result = ordersService.cancelOrder(orderCancelReq);
        return result;
    }

    /** 주문 목록 조회 : 유저**/
    @GetMapping("/list/user/{userId}")
    public BaseResponse<List<OrderUserSearchRes>> searchForUser(@PathVariable Long userId) {
        BaseResponse<List<OrderUserSearchRes>> result = ordersService.searchForUser(userId);
        return result;
    }

    /** 주문 상세내역 조회 : 유저**/
    @GetMapping("/list/user/{userId}/{ordersId}")
    public BaseResponse<List<OrderUserSearchDetailRes>> searchForUserDetail(@PathVariable Long userId,@PathVariable Long ordersId) {
        BaseResponse<List<OrderUserSearchDetailRes>> result = ordersService.searchForUserDetail(userId,ordersId);
        return result;
    }

    /** 주문 내역 조회 : 사업자**/
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/company/{companyId}")
    public BaseResponse<List<OrdersQueryProjection>> searchForCompany(@PathVariable Long companyId, OrderSearchReq search) {
        BaseResponse<List<OrdersQueryProjection>> result = ordersService.searchForCompany(companyId, search);
        return result;
    }

    /** 주문 상세내역 조회 : 사업자**/
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/company/{companyId}/{orderId}")
    public BaseResponse<List<OrdersQueryProjection>> searchForCompanyDetail(@PathVariable Long companyId,@PathVariable Long orderId) {
        BaseResponse<List<OrdersQueryProjection>> result = ordersService.searchForCompanyDetail(companyId, orderId);
        return result;
    }

    /** 배송 상태 변경 : 사업자 **/
//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/statechange")
    public BaseResponse changeDeliveryState(@RequestBody OrderSearchReq orderSearchReq) {
        BaseResponse result = ordersService.changeDeliveryState(orderSearchReq);
        return result;
    }

    /** 송장 등록 : 사업자 **/
    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/invoice")
    public BaseResponse inputInvoice(@RequestBody OrderInvoiceReq orderInvoiceReq) {
        BaseResponse result = ordersService.inputInvoice(orderInvoiceReq);
        return result;
    }

    //String impUid
    /** kakao pay 결제 **/
    @PostMapping(value = "/kakao")
    public  ResponseEntity<String> kakao(@RequestBody OrderCreateReq orderCreateReq) throws IamportResponseException, IOException {

        System.out.println(orderCreateReq.getUserId());
        System.out.println(orderCreateReq.getTotalPrice());

        IamportResponse<Payment> info = ordersService.getPaymentInfo(orderCreateReq.getImpUid());
        BaseResponse result = ordersService.createOrder(orderCreateReq);

        //if(resultPay) {
            System.out.println("ok");

            return ResponseEntity.ok("ok");
        //}
//        else {
//            System.out.println("error");
//            //paymentService.refund(impUid, info);
//            return ResponseEntity.ok("error");
//        }
    }
}
