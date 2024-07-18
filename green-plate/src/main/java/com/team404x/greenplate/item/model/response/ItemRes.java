package com.team404x.greenplate.item.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemRes {
	private String name;
	private Integer price;
	private Integer calorie;
	private String imageUrl;
	private Integer discountPrice;
	private String companyName;
}
