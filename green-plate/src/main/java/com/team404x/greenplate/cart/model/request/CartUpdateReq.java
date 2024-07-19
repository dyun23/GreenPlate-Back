package com.team404x.greenplate.cart.model.request;

import lombok.Getter;

@Getter
public class CartUpdateReq {
    private Long cartId;
    private Integer quantity;
}
