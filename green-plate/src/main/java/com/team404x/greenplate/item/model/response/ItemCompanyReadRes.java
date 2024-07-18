package com.team404x.greenplate.item.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemCompanyReadRes {
    private final String item_id;
    private final String name;
    private final int price;
    private final int stock;
    private final String state;
    private final String image_url;
    private final int discount_price;
}
