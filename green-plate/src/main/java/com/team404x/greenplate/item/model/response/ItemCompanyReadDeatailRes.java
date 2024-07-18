package com.team404x.greenplate.item.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemCompanyReadDeatailRes {
    private final String product_id;
    private final String name;
    private final String contents;
    private final int price;
    private final int stock;
    private final int calorie;
    private final String state;
    private final String image_url;
    private final int discount_price;
    private final String main_category;
    private final String sub_category;
    private final String keyword_lists;
}
