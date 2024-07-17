package com.team404x.greenplate.recipe.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDetailsItemRes {
    private Long itemId;
    private String name;
    private Integer discountPrice;
    private String itemUrl;
}
