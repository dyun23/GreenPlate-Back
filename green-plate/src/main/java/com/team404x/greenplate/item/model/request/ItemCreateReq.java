package com.team404x.greenplate.item.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemCreateReq {
   private String name;
   private String contents;
   private int price;
   private int stock;
   private int calorie;
   private String state;

   private int discountPrice;
   private String mainCategory;
   private String subCategory;

}
