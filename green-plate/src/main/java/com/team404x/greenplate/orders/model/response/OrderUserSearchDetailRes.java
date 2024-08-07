package com.team404x.greenplate.orders.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderUserSearchDetailRes {
    private Long order_id;
    private Long orderDetail_id;
    private int order_totalPrice;
    private LocalDateTime order_date;
    private String itemName;
    private String itemImageUrl;
    private Integer price;
    private Integer cnt;
    private String order_state;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String phoneNum;
    private String invoice;
    private boolean refund_yn;
}
