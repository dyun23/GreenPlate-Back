package com.team404x.greenplate.cart.model.request;

import lombok.Getter;

@Getter
public class CartAddReq {
    private Long itemId;
    private int quantity;
}
