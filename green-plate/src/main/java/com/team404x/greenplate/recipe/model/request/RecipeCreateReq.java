package com.team404x.greenplate.recipe.model.request;

import lombok.Getter;


@Getter
public class RecipeCreateReq {
    private String title;
    private String contents;
    private Long[] itemList;
    private String[] keywordList;
}
