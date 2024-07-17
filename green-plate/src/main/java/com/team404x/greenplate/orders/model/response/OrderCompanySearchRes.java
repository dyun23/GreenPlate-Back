package com.team404x.greenplate.orders.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderCompanySearchRes {
    private Long order_id;
    private Long item_id;
    private Long item_name;
    private Long price;
    private int cnt;
    private LocalDateTime order_date;
    private String order_state;
    private boolean refund_yn;
}
