package com.team404x.greenplate.orders.model.requset;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderCreateReq {
    private Long userId;

    private LocalDateTime orderDate;

    private Long totalPrice;

    private Integer totalQuantity;

    private Boolean refundYn;

    private List<OrderDetailDto> orderDetailList;

    @Data
    public static class OrderDetailDto {
        private Long itemId;
        private int cnt;
        private Long price;
    }
}

