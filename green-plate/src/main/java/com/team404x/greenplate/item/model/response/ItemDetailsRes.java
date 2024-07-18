package com.team404x.greenplate.item.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemDetailsRes {
	private Long id;
	private String name;
	private Integer price;
	private Integer calorie;
	private String imageUrl;
	private Integer discountPrice;
	private int stock;
	private String contents;
	private String companyName;
}
