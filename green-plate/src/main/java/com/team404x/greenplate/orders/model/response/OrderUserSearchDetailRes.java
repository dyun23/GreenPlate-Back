package com.team404x.greenplate.orders.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderUserSearchDetailRes {
    private Long order_id;
    private Long orderDetail_id;
    private LocalDateTime order_date;
    private Long price;
    private Integer cnt;
    private String order_state;
    private boolean refund_yn;
}
