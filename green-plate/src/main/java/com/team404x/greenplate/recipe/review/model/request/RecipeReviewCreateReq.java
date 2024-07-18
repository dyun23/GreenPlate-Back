package com.team404x.greenplate.recipe.review.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeReviewCreateReq {
    private Long recipeId;
    private Long userId;
    private Integer rating;
    private String contents;
}
