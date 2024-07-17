package com.team404x.greenplate.orders.controller;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.orders.model.entity.OrderDetail;
import com.team404x.greenplate.orders.model.entity.OrderStatus;
import com.team404x.greenplate.orders.model.entity.OrdersQueryProjection;
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

    //수정중
    /** 상품 주문 내역 조회 : 사업자**/
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/company/{companyId}")
    public BaseResponse<List<OrdersQueryProjection>> searchForCompany(@PathVariable Long companyId, OrderSearchReq search) {
        BaseResponse<List<OrdersQueryProjection>> result = ordersService.searchForCompany(companyId, search);
        return result;
    }


}
