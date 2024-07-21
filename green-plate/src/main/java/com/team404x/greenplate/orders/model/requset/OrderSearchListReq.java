package com.team404x.greenplate.orders.model.requset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@NoArgsConstructor

public class OrderSearchListReq {
    private String status;
}
