package com.team404x.greenplate.orders.model.requset;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderCreateReq {
    private Long userId;
    private String payment;
    private Long totalPrice;
    private Integer totalQuantity;
    private Boolean refundYn;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String phoneNum;
    private List<OrderDetailDto> orderDetailList;

    @Data
    public static class OrderDetailDto {
        private Long itemId;
        private int cnt;
        private Long discountPrice;
        private Long price;
    }
}

