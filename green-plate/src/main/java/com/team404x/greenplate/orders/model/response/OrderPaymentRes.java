package com.team404x.greenplate.orders.model.response;

import com.team404x.greenplate.orders.model.requset.OrderCreateReq;
import lombok.Data;

import java.util.List;

@Data
public class OrderPaymentRes {
    private Long userId;
    private String payment;
    private Long totalPrice;
    private Integer totalQuantity;
    private Boolean refundYn;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String phoneNum;
    private List<OrderCreateReq.OrderDetailDto> orderDetailList;

    @Data
    public static class OrderDetailDto {
        private Long itemId;
        private int cnt;
        private Long discountPrice;
        private Long price;
    }
}
