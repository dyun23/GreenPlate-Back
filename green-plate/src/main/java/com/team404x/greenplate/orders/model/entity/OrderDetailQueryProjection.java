package com.team404x.greenplate.orders.model.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderDetailQueryProjection {

    private Long order_id;
    private Long item_id;
    private String item_name;
    private String item_imgUrl;
    private Integer price;
    private Integer cnt;
    private LocalDateTime order_date;
    private String order_state;
    private boolean refund_yn;
    private String zipCode;
    private String address;
    private String phoneNum;
    private String invoice;

    @QueryProjection
    public OrderDetailQueryProjection(Long order_id, Long item_id, String item_name, String item_imgUrl, Integer price, int cnt, LocalDateTime order_date, String order_state, boolean refund_yn, String zipCode, String address, String phoneNum, String invoice) {
        this.order_id = order_id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_imgUrl = item_imgUrl;
        this.price = price;
        this.cnt = cnt;
        this.order_date = order_date;
        this.order_state = order_state;
        this.refund_yn = refund_yn;
        this.zipCode = zipCode;
        this.address = address;
        this.phoneNum = phoneNum;
        this.invoice = invoice;
    }

}
