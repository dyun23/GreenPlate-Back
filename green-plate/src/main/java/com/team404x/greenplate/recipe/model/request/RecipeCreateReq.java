package com.team404x.greenplate.recipe.model.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RecipeCreateReq {
    private String title;
    private String contents;
    private Long[] itemList;
    private String[] keywordList;
}
