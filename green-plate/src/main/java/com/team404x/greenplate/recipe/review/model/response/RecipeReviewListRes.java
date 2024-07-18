package com.team404x.greenplate.recipe.review.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeReviewListRes {
    private Long reviewId;
    private String userName;
    private Integer rating;
    private String contents;
}
