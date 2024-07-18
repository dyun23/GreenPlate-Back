package com.team404x.greenplate.item.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ItemUpdateReq {
    private final String companyId;
    private final String itemId;
    private final String name;
    private final String contents;
    private final int price;
    private final int stock;
    private final int calorie;
    private final String state;
    private final String imageUrl;
    private final int discountPrice;
    private final String mainCategory;
    private final String subCategory;


}
