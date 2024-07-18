package com.team404x.greenplate.item.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ItemUpdateReq {
    private final String company_id;
    private final String item_id;
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


}
