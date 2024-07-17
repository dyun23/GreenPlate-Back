package com.team404x.greenplate.recipe.model.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class RecipeCreateReq {
    private Long companyId;
    private String title;
    private String contents;
    private Long[] itemList;
}
