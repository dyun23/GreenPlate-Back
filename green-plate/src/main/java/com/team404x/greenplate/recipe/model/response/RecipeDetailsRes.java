package com.team404x.greenplate.recipe.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDetailsRes {
    private Long recipeId;
    private String title;
    private String contents;
    private String imageUrl;
    private Integer totalCalorie;
    private List<RecipeDetailsItemRes> itemList;
    private List<String> keywords;
    private String memberName;
    private Long memberId;
    private String role;
    private String date;
}
