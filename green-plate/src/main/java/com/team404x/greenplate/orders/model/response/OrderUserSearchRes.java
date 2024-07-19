package com.team404x.greenplate.orders.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderUserSearchRes {
    private Long order_id;
    private LocalDateTime order_date;
    private Integer total_price;
    private Integer total_cnt;
    private String order_state;
    private boolean refund_yn;
}
