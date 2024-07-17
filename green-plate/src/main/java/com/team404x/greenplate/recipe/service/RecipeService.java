package com.team404x.greenplate.recipe.service;

import com.team404x.greenplate.common.s3.S3FileUploadSevice;
import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.item.repository.ItemRepository;
import com.team404x.greenplate.keyword.repository.KeywordRepository;
import com.team404x.greenplate.recipe.item.RecipeItem;
import com.team404x.greenplate.recipe.keyword.entity.RecipeKeyword;
import com.team404x.greenplate.recipe.model.entity.Recipe;
import com.team404x.greenplate.recipe.model.request.RecipeCreateReq;
import com.team404x.greenplate.recipe.model.request.RecipeUpdateReq;
import com.team404x.greenplate.recipe.repository.RecipeItemRepository;
import com.team404x.greenplate.recipe.repository.RecipeKeywordRepository;
import com.team404x.greenplate.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeItemRepository recipeItemRepository;
    private final S3FileUploadSevice s3FileUploadSevice;
    private final ItemRepository itemRepository;
    private final KeywordRepository keywordRepository;
    private final RecipeKeywordRepository recipeKeywordRepository;

    public void createRecipe(RecipeCreateReq request, MultipartFile image) {
        Recipe recipe = Recipe.builder()
                .title(request.getTitle())
                .contents(request.getContents())
                .imageUrl(s3FileUploadSevice.upload("recipe", request.getCompanyId(), image))
                .totalCalorie(itemRepository.getCalorieSum(request.getItemList()))
                .company(Company.builder().id(request.getCompanyId()).build())
                .build();
        Recipe result = recipeRepository.save(recipe);
        saveRecipeItem(result, request.getItemList());
        saveRecipeKeyword(result, request.getKeywordList());
    }

    public void updateRecipe(RecipeUpdateReq request, MultipartFile image) {
        Recipe recipe = Recipe.builder()
                .id(request.getRecipeId())
                .title(request.getTitle())
                .contents(request.getContents())
                .imageUrl(s3FileUploadSevice.upload("recipe", request.getCompanyId(), image))
                .totalCalorie(itemRepository.getCalorieSum(request.getItemList()))
                .company(Company.builder().id(request.getCompanyId()).build())
                .build();
        Recipe result = recipeRepository.save(recipe);

        recipeItemRepository.deleteByRecipe(result);
        saveRecipeItem(result, request.getItemList());

        recipeKeywordRepository.deleteByRecipe(result);
        saveRecipeKeyword(result, request.getKeywordList());
    }

    void saveRecipeItem(Recipe recipe, Long[] items) {
        List<RecipeItem> recipeItems = new ArrayList<>();
        for (Long itemId : items) {
            itemRepository.findById(itemId).ifPresent(item -> {
                RecipeItem recipeItem = RecipeItem.builder()
                        .item(item)
                        .recipe(recipe)
                        .build();
                recipeItems.add(recipeItem);
            });
        }
        recipeItemRepository.saveAll(recipeItems);
    }

    void saveRecipeKeyword(Recipe recipe, String[] keywords) {
        List<RecipeKeyword> recipeKeywords = new ArrayList<>();
        for (String keyword : keywords) {
            RecipeKeyword recipeKeyword = RecipeKeyword.builder()
                    .keyword(keywordRepository.findByName(keyword))
                    .recipe(recipe)
                    .build();
            recipeKeywords.add(recipeKeyword);
        }
        recipeKeywordRepository.saveAll(recipeKeywords);
    }
}
