package com.team404x.greenplate.cart.model.request;

import lombok.Getter;

@Getter
public class CartDeleteListReq {
    private Long[] cartIdList;
}
