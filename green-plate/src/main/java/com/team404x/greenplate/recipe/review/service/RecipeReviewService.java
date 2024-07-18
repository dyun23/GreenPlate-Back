package com.team404x.greenplate.recipe.review.service;

import com.team404x.greenplate.recipe.model.entity.Recipe;
import com.team404x.greenplate.recipe.review.model.RecipeReview;
import com.team404x.greenplate.recipe.review.model.request.RecipeReviewCreateReq;
import com.team404x.greenplate.recipe.review.repository.RecipeReviewRepository;
import com.team404x.greenplate.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeReviewService {
    private final RecipeReviewRepository recipeReviewRepository;
    public void saveReview(RecipeReviewCreateReq request) {
        recipeReviewRepository.save(RecipeReview.builder()
                        .contents(request.getContents())
                        .rating(request.getRating())
                        .user(User.builder().id(request.getUserId()).build())
                        .recipe(Recipe.builder().id(request.getRecipeId()).build())
                .build());
    }
}
