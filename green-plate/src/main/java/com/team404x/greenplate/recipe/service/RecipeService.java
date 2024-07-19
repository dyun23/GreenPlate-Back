package com.team404x.greenplate.recipe.service;

//import com.team404x.greenplate.common.s3.S3FileUploadSevice;
import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import com.team404x.greenplate.item.model.entity.Item;
import com.team404x.greenplate.item.repository.ItemRepository;
import com.team404x.greenplate.keyword.repository.KeywordRepository;
import com.team404x.greenplate.recipe.item.RecipeItem;
import com.team404x.greenplate.recipe.keyword.entity.RecipeKeyword;
import com.team404x.greenplate.recipe.model.entity.Recipe;
import com.team404x.greenplate.recipe.model.request.RecipeCreateReq;
import com.team404x.greenplate.recipe.model.request.RecipeUpdateReq;
import com.team404x.greenplate.recipe.model.response.RecipeDetailsItemRes;
import com.team404x.greenplate.recipe.model.response.RecipeDetailsRes;
import com.team404x.greenplate.recipe.model.response.RecipeListRes;
import com.team404x.greenplate.recipe.repository.RecipeItemRepository;
import com.team404x.greenplate.recipe.repository.RecipeKeywordRepository;
import com.team404x.greenplate.recipe.repository.RecipeRepository;
import com.team404x.greenplate.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeItemRepository recipeItemRepository;
//    private final S3FileUploadSevice s3FileUploadSevice;
    private final ItemRepository itemRepository;
    private final KeywordRepository keywordRepository;
    private final RecipeKeywordRepository recipeKeywordRepository;

    public void createRecipe(CustomUserDetails customUserDetails, RecipeCreateReq request, MultipartFile image) {
        Recipe recipe;
        Long id = customUserDetails.getId();
        if (customUserDetails.getAuthorities().iterator().next().getAuthority().equals("ROLE_COMPANY")) {
            recipe = Recipe.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
//                    .imageUrl(s3FileUploadSevice.upload("recipe", id, image))
                    .totalCalorie(itemRepository.getCalorieSum(request.getItemList()))
                    .company(Company.builder().id(id).build())
                    .build();
        } else {
            recipe = Recipe.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
//                    .imageUrl(s3FileUploadSevice.upload("recipe", id, image))
                    .totalCalorie(itemRepository.getCalorieSum(request.getItemList()))
                    .user(User.builder().id(id).build())
                    .build();
        }
        Recipe result = recipeRepository.save(recipe);
        saveRecipeItem(result, request.getItemList());
        saveRecipeKeyword(result, request.getKeywordList());
    }

    public void updateRecipe(CustomUserDetails customUserDetails, RecipeUpdateReq request, MultipartFile image) {
        Long id = customUserDetails.getId();
        Recipe recipe;
        if (customUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_COMPANY"))) {
            recipe = Recipe.builder()
                .id(request.getRecipeId())
                    .title(request.getTitle())
                    .contents(request.getContents())
//                    .imageUrl(s3FileUploadSevice.upload("recipe",id, image))
                    .totalCalorie(itemRepository.getCalorieSum(request.getItemList()))
                    .company(Company.builder().id(id).build())
                    .build();
        } else {
            recipe = Recipe.builder()
                    .id(request.getRecipeId())
                    .title(request.getTitle())
                    .contents(request.getContents())
//                    .imageUrl(s3FileUploadSevice.upload("recipe", id, image))
                    .totalCalorie(itemRepository.getCalorieSum(request.getItemList()))
                    .user(User.builder().id(id).build())
                    .build();
        }
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

    public List<RecipeListRes> listRecipes(String search) {
        List<Recipe> recipeList;
        if (search == null || search.isEmpty()) {
            recipeList = recipeRepository.findAll();
        } else {
            recipeList = recipeRepository.findByTitleContains(search);
        }
        List<RecipeListRes> recipeListRes = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            if (recipe.getUser() == null) {
                RecipeListRes res = RecipeListRes.builder()
                        .recipeId(recipe.getId())
                        .title(recipe.getTitle())
                        .imageUrl(recipe.getImageUrl())
                        .keywords(keywordRepository.findByRecipeKeywordsRecipeId(recipe.getId()))
                        .memberId(recipe.getCompany().getId())
                        .memberName(recipe.getCompany().getName())
                        .role("ROLE_COMPANY")
                        .build();
                recipeListRes.add(res);
            } else {
                RecipeListRes res = RecipeListRes.builder()
                        .recipeId(recipe.getId())
                        .title(recipe.getTitle())
                        .imageUrl(recipe.getImageUrl())
                        .keywords(keywordRepository.findByRecipeKeywordsRecipeId(recipe.getId()))
                        .memberId(recipe.getUser().getId())
                        .memberName(recipe.getUser().getName())
                        .role("ROLE_USER")
                        .build();
                recipeListRes.add(res);
            }
        }
        return recipeListRes;
    }

    public RecipeDetailsRes readRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe == null) {
            return null;
        }
        List<Item> itemList = itemRepository.findByRecipeItemsRecipeId(recipe.getId());
        List<RecipeDetailsItemRes> recipeDetailsItemResList = new ArrayList<>();
        for (Item item : itemList) {
            RecipeDetailsItemRes detailsItem = RecipeDetailsItemRes.builder()
                    .itemId(item.getId())
                    .name(item.getName())
                    .discountPrice(item.getDiscountPrice())
                    .itemUrl(item.getImageUrl())
                    .build();
            recipeDetailsItemResList.add(detailsItem);
        }
        if (recipe.getUser() == null) {
            return RecipeDetailsRes.builder()
                    .recipeId(recipe.getId())
                    .title(recipe.getTitle())
                    .contents(recipe.getContents())
                    .imageUrl(recipe.getImageUrl())
                    .totalCalorie(recipe.getTotalCalorie())
                    .itemList(recipeDetailsItemResList)
                    .keywords(keywordRepository.findByRecipeKeywordsRecipeId(recipe.getId()))
                    .memberId(recipe.getCompany().getId())
                    .role("ROLE_COMPANY")
                    .build();
        } else {
            return RecipeDetailsRes.builder()
                    .recipeId(recipe.getId())
                    .title(recipe.getTitle())
                    .contents(recipe.getContents())
                    .imageUrl(recipe.getImageUrl())
                    .totalCalorie(recipe.getTotalCalorie())
                    .itemList(recipeDetailsItemResList)
                    .keywords(keywordRepository.findByRecipeKeywordsRecipeId(recipe.getId()))
                    .memberId(recipe.getUser().getId())
                    .role("ROLE_USER")
                    .build();
        }
    }
}
