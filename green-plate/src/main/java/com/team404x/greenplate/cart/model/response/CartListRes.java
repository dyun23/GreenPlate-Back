package com.team404x.greenplate.cart.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartListRes {
    private Long cartId;
    private Long itemId;
    private String itemName;
    private Integer discountPrice;
    private String imageUrl;
    private Integer quantity;
}
