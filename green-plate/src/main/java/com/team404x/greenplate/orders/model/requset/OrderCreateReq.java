package com.team404x.greenplate.orders.model.requset;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class OrderCreateReq {
    private Long userId;
    private Integer totalPrice; //총주문금액
    private Integer totalQuantity; //총 주문수량
    private String zipCode;
    private String address;
    private String phoneNum;
    private String recipient;
    private List<OrderDetailDto> orderDetailList;

    @Builder
    @Data
    public static class OrderDetailDto {
        private Long itemId;
        private Integer cnt;
        private Integer discountPrice;
        private Integer price;
    }
}

