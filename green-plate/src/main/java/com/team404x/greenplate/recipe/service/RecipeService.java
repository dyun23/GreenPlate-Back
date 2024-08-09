package com.team404x.greenplate.recipe.service;

import com.team404x.greenplate.common.GlobalMessage;
import com.team404x.greenplate.common.s3.S3FileUploadSevice;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
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

    public void createRecipe(CustomUserDetails customUserDetails, RecipeCreateReq request, MultipartFile image) throws Exception {
        if (request.getTitle() == null || request.getTitle().isEmpty() || request.getItemList().length == 0 || request.getContents() == null || request.getContents().isEmpty()) {
            throw new Exception("필수값");
        }
        Recipe recipe;
        Long id = customUserDetails.getId();
        if (customUserDetails.getAuthorities().iterator().next().getAuthority().equals(GlobalMessage.ROLE_COMPANY.getMessage())) {
            recipe = Recipe.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .imageUrl(s3FileUploadSevice.upload("recipe", id, image))
                    .totalCalorie(itemRepository.getCalorieSum(request.getItemList()))
                    .company(Company.builder().id(id).build())
                    .build();
        } else {
            recipe = Recipe.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .imageUrl(s3FileUploadSevice.upload("recipe", id, image))
                    .totalCalorie(itemRepository.getCalorieSum(request.getItemList()))
                    .user(User.builder().id(id).build())
                    .delYn(false)
                    .build();
        }
        Recipe result = recipeRepository.save(recipe);
        saveRecipeItem(result, request.getItemList());
        saveRecipeKeyword(result, request.getKeywordList());
    }

    public void updateRecipe(CustomUserDetails customUserDetails, RecipeUpdateReq request, MultipartFile image) throws Exception {
        if (request.getTitle() == null || request.getTitle().isEmpty() || request.getItemList().length == 0 || request.getContents() == null || request.getContents().isEmpty()) {
            throw new Exception("필수값");
        }
        Long id = customUserDetails.getId();
        Recipe recipe;
        if (customUserDetails.getAuthorities().iterator().next().getAuthority().equals(GlobalMessage.ROLE_COMPANY.getMessage())) {
            if (!recipeRepository.findById(request.getRecipeId()).get().getCompany().getId().equals(id)) {
                throw new Exception("본인아님");
            }
            recipe = Recipe.builder()
                .id(request.getRecipeId())
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .imageUrl(s3FileUploadSevice.upload("recipe",id, image))
                    .totalCalorie(itemRepository.getCalorieSum(request.getItemList()))
                    .company(Company.builder().id(id).build())
                    .build();
        } else {
            recipe = Recipe.builder()
                    .id(request.getRecipeId())
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .imageUrl(s3FileUploadSevice.upload("recipe", id, image))
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
                RecipeItem recipeItem = RecipeItem.builder()
                        .item(Item.builder().id(itemId).build())
                        .recipe(recipe)
                        .build();
                recipeItems.add(recipeItem);
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

    public List<RecipeListRes> listRecipes(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipeList;
        if (search == null || search.isEmpty() || search.equals(" ")) {
            recipeList = recipeRepository.findAllWithKeywordsAndUserCompany(pageable);
        } else {
            recipeList = recipeRepository.findByTitleContainsWithKeywords(pageable, search);
        }
        List<RecipeListRes> recipeListRes = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            List<String> keywordNames = new ArrayList<>();
            for (RecipeKeyword keyword : recipe.getRecipeKeywords()) {
                keywordNames.add(keyword.getKeyword().getName());
            }
            RecipeListRes res;
            if (recipe.getUser() == null) {
                res = RecipeListRes.builder()
                        .recipeId(recipe.getId())
                        .title(recipe.getTitle())
                        .imageUrl(recipe.getImageUrl())
                        .keywords(keywordNames)
                        .memberId(recipe.getCompany().getId())
                        .memberName(recipe.getCompany().getName())
                        .role(GlobalMessage.ROLE_COMPANY.getMessage())
                        .build();
            } else {
                res = RecipeListRes.builder()
                        .recipeId(recipe.getId())
                        .title(recipe.getTitle())
                        .imageUrl(recipe.getImageUrl())
                        .keywords(keywordNames)
                        .memberId(recipe.getUser().getId())
                        .memberName(recipe.getUser().getName())
                        .role(GlobalMessage.ROLE_USER.getMessage())
                        .build();
            }
            recipeListRes.add(res);
        }
        return recipeListRes;
    }

    public Page<RecipeListRes> list (Pageable pageable) {
        Page<Recipe> recipeList = recipeRepository.findAll(pageable);
        // 함수 마저 만들기
        return recipeList.map(recipe -> {
            List<String> keywordNames = recipe.getRecipeKeywords().stream()
                .map(recipeKeyword -> recipeKeyword.getKeyword().getName())
                .toList();

            return RecipeListRes.builder()
                .recipeId(recipe.getId())
                .title(recipe.getTitle())
                .imageUrl(recipe.getImageUrl())
                .keywords(keywordNames)
                .memberId(recipe.getUser().getId())
                .memberName(recipe.getUser().getName())
                .build();
        });
    }

    public RecipeDetailsRes readRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe == null) {
            return null;
        }
        List<Item> itemList = itemRepository.findByRecipeItemsRecipeId(recipe.getId());
        List<RecipeDetailsItemRes> recipeDetailsItemResList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
                    .date(recipe.getCreatedDate().format(formatter))
                    .totalCalorie(recipe.getTotalCalorie())
                    .itemList(recipeDetailsItemResList)
                    .keywords(keywordRepository.findByRecipeKeywordsRecipeId(recipe.getId()))
                    .memberId(recipe.getCompany().getId())
                    .memberName(recipe.getCompany().getName())
                    .role(GlobalMessage.ROLE_COMPANY.getMessage())
                    .build();
        } else {
            return RecipeDetailsRes.builder()
                    .recipeId(recipe.getId())
                    .title(recipe.getTitle())
                    .contents(recipe.getContents())
                    .imageUrl(recipe.getImageUrl())
                    .date(recipe.getCreatedDate().format(formatter))
                    .totalCalorie(recipe.getTotalCalorie())
                    .itemList(recipeDetailsItemResList)
                    .keywords(keywordRepository.findByRecipeKeywordsRecipeId(recipe.getId()))
                    .memberId(recipe.getUser().getId())
                    .memberName(recipe.getUser().getName())
                    .role(GlobalMessage.ROLE_USER.getMessage())
                    .build();
        }
    }
}
