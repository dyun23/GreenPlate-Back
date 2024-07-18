package com.team404x.greenplate.orders.controller;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.orders.model.entity.OrdersQueryProjection;
import com.team404x.greenplate.orders.model.requset.OrderInvoiceReq;
import com.team404x.greenplate.orders.model.requset.OrderSearchReq;
import com.team404x.greenplate.orders.model.response.OrderUserSearchRes;
import com.team404x.greenplate.orders.service.OrdersService;
import com.team404x.greenplate.orders.model.requset.OrderCreateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
public class OrdersController {
    private final OrdersService ordersService;

    /** 상품 주문**/
    @PostMapping(value = "/create")
    public BaseResponse create(@RequestBody OrderCreateReq orderCreateReq) {
        BaseResponse result = ordersService.createOrder(orderCreateReq);
        return result;
    }

    /** 주문 취소**/
    @PutMapping(value = "/cancel/{orderId}")
    public BaseResponse cancel(@PathVariable Long orderId) {
        BaseResponse result = ordersService.cancelOrder(orderId);
        return result;
    }

    /** 상품 주문 내역 조회 : 유저**/
    @GetMapping("/list/user/{userId}")
    public BaseResponse<List<OrderUserSearchRes>> search(@PathVariable Long userId) {
        BaseResponse<List<OrderUserSearchRes>> result = ordersService.search(userId);
        return result;
    }

    /** 상품 주문 내역 조회 : 사업자**/
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/company/{companyId}")
    public BaseResponse<List<OrdersQueryProjection>> searchForCompany(@PathVariable Long companyId, OrderSearchReq search) {
        BaseResponse<List<OrdersQueryProjection>> result = ordersService.searchForCompany(companyId, search);
        return result;
    }

    /** 상품 주문 상세내역 조회 : 사업자**/
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/company/{companyId}/{orderId}")
    public BaseResponse<List<OrdersQueryProjection>> searchForCompanyDetail(@PathVariable Long companyId,@PathVariable Long orderId) {
        BaseResponse<List<OrdersQueryProjection>> result = ordersService.searchForCompanyDetail(companyId, orderId);
        return result;
    }

    /** 상품 배송 상태 변경 : 사업자 **/
//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/statechange")
    public BaseResponse changeDeliveryState(@RequestBody OrderSearchReq orderSearchReq) {
        BaseResponse result = ordersService.changeDeliveryState(orderSearchReq);
        return result;
    }

    /** 상품 송장 등록 : 사업자 **/
    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/invoice")
    public BaseResponse inputInvoice(@RequestBody OrderInvoiceReq orderInvoiceReq) {
        BaseResponse result = ordersService.inputInvoice(orderInvoiceReq);
        return result;
    }

}
