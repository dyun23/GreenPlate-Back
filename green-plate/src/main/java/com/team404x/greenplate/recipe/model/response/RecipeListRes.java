package com.team404x.greenplate.recipe.model.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeListRes {
    private Long recipeId;
    private String title;
    private String imageUrl;
    private List<String> keywords;
    private Long memberId;
    private String memberName;
    private String role;
}
