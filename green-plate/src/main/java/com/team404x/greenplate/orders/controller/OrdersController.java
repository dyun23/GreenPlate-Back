package com.team404x.greenplate.orders.controller;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.orders.model.entity.OrderDetail;
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

    /** 상품 주문**/
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
    @GetMapping("/list/company/{adminId}")
    public BaseResponse<List<OrderDetail>> searchForAdmin(@PathVariable Long companyId) {
        BaseResponse<List<OrderDetail>> result = ordersService.searchForCompany(companyId);
        return result;
    }


}
