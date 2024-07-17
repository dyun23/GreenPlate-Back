package com.team404x.greenplate.orders.model.entity;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.team404x.greenplate.user.address.entity.Address;
import com.team404x.greenplate.user.model.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrdersQueryProjection {

    private Long order_id;
    private Long item_id;
    private String item_name;
    private Long price;
    private int cnt;
    private LocalDateTime order_date;
    private String order_state;
    private boolean refund_yn;

    @QueryProjection
    public OrdersQueryProjection(Long order_id, Long item_id, String item_name, Long price, int cnt, LocalDateTime order_date, String order_state, boolean refund_yn) {
        this.order_id = order_id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.price = price;
        this.cnt = cnt;
        this.order_date = order_date;
        this.order_state = order_state;
        this.refund_yn = refund_yn;
    }


}
