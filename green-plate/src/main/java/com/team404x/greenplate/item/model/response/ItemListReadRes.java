package com.team404x.greenplate.item.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemListReadRes {
    private Long itemId;
    private String name;
    private Integer price;
    private Integer stock;
    private String state;
    private String imageUrl;
    private Integer discountPrice;
}
