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


    @PostMapping(value = "/payment")
    public BaseResponse<OrderPaymentRes> chosePayment(@RequestBody OrderCreateReq orderCreateReq) {
        BaseResponse<OrderPaymentRes> result = ordersService.chosePayment(orderCreateReq);
        return result;
    }


    @PostMapping(value = "/create")
    public BaseResponse create(@RequestBody OrderCreateReq orderCreateReq) {
        BaseResponse result = ordersService.createOrder(orderCreateReq);
        return result;
    }


    @PutMapping(value = "/cancel")
    public BaseResponse cancel(@RequestBody OrderCancelReq orderCancelReq) {
        BaseResponse result = ordersService.cancelOrder(orderCancelReq);
        return result;
    }


    @GetMapping("/list/user/{userId}")
    public BaseResponse<List<OrderUserSearchRes>> searchForUser(@PathVariable Long userId) {
        BaseResponse<List<OrderUserSearchRes>> result = ordersService.searchForUser(userId);
        return result;
    }


    @GetMapping("/list/user/{userId}/{ordersId}")
    public BaseResponse<List<OrderUserSearchDetailRes>> searchForUserDetail(@PathVariable Long userId,@PathVariable Long ordersId) {
        BaseResponse<List<OrderUserSearchDetailRes>> result = ordersService.searchForUserDetail(userId,ordersId);
        return result;
    }


    @GetMapping("/list/company/{companyId}")
    public BaseResponse<List<OrdersQueryProjection>> searchForCompany(@PathVariable Long companyId, OrderSearchReq search) {
        BaseResponse<List<OrdersQueryProjection>> result = ordersService.searchForCompany(companyId, search);
        return result;
    }


    @GetMapping("/list/company/{companyId}/{orderId}")
    public BaseResponse<List<OrdersQueryProjection>> searchForCompanyDetail(@PathVariable Long companyId,@PathVariable Long orderId) {
        BaseResponse<List<OrdersQueryProjection>> result = ordersService.searchForCompanyDetail(companyId, orderId);
        return result;
    }


    @PutMapping("/statechange")
    public BaseResponse changeDeliveryState(@RequestBody OrderSearchReq orderSearchReq) {
        BaseResponse result = ordersService.changeDeliveryState(orderSearchReq);
        return result;
    }


    @PostMapping(value = "/invoice")
    public BaseResponse inputInvoice(@RequestBody OrderInvoiceReq orderInvoiceReq) {
        BaseResponse result = ordersService.inputInvoice(orderInvoiceReq);
        return result;
    }


    @PostMapping(value = "/kakaoPay")
    public  ResponseEntity<String> kakaoPay(@RequestBody OrderCreateReq orderCreateReq) throws IamportResponseException, IOException {
        IamportResponse<Payment> info = ordersService.getPaymentInfo(orderCreateReq.getImpUid());
        System.out.println(orderCreateReq.getImpUid());
        Boolean payCheck = ordersService.payCheck(orderCreateReq,info);
        if(payCheck) {
            System.out.println("ok");
            BaseResponse resultPay = ordersService.createOrder(orderCreateReq);
            return ResponseEntity.ok("ok");
        }
        else {
            System.out.println("error");
            ordersService.refund(orderCreateReq, info);
            return ResponseEntity.ok("error");
        }
    }


    @PostMapping(value = "/kakaoRefund")
    public  BaseResponse kakaoRefund(@RequestBody OrderCancelReq orderCancelReq) throws IamportResponseException, IOException {
        BaseResponse result = ordersService.kakaoPayRefund(orderCancelReq);
        return result;
    }
}
