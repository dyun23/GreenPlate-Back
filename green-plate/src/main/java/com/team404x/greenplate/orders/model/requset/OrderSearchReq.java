package com.team404x.greenplate.orders.model.requset;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderSearchReq {
    private Long orderId;
    private String status;
}
