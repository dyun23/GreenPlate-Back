package com.team404x.greenplate.recipe.service;

import com.team404x.greenplate.common.s3.S3FileUploadSevice;
import com.team404x.greenplate.company.entity.Company;
import com.team404x.greenplate.item.repository.ItemRepository;
import com.team404x.greenplate.recipe.model.entity.Recipe;
import com.team404x.greenplate.recipe.model.request.RecipeCreateReq;
import com.team404x.greenplate.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final S3FileUploadSevice s3FileUploadSevice;
    private final ItemRepository itemRepository;

    public void createRecipe(RecipeCreateReq request, MultipartFile image) {
        String imageUrl = s3FileUploadSevice.upload("recipe", request.getCompanyId(), image);
        Recipe recipe = Recipe.builder()
                .title(request.getTitle())
                .contents(request.getContents())
                .imageUrl(imageUrl)
                .totalCalorie(itemRepository.getCalorieSum(request.getItemList()))
                .company(Company.builder().id(request.getCompanyId()).build())
                .build();
        recipeRepository.save(recipe);
    }
}
