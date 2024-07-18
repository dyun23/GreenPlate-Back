package com.team404x.greenplate.orders.model.requset;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class OrderInvoiceReq {
    private Long orderId;
    private String invoiceNum;
}
