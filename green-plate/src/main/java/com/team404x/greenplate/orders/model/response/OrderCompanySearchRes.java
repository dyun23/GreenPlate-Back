package com.team404x.greenplate.orders.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderCompanySearchRes {
    private Long order_id;
    private LocalDateTime order_date;
    private Long total_price;
    private int total_cnt;
    private String order_state;
    private boolean refund_yn;
}
