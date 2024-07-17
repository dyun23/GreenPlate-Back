package com.team404x.greenplate.recipe.model.request;

import lombok.Getter;

@Getter
public class RecipeUpdateReq {
    private Long recipeId;
    private Long companyId;
    private String title;
    private String contents;
    private Long[] itemList;
    private String[] keywordList;
}
