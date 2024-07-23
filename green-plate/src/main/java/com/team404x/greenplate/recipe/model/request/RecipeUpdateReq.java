package com.team404x.greenplate.recipe.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeUpdateReq {
    private Long recipeId;
    private String title;
    private String contents;
    private Long[] itemList;
    private String[] keywordList;
}
