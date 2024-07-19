package com.team404x.greenplate.item.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemSearchRes {
    private Long itemId;
    private String name;
    private Integer price;
    private Integer stock;
    private String state;
    private String Imageurl;
    private Integer discountPrice;
    private String mainCategory;
    private String subCategory;
    private String keyword;
}
