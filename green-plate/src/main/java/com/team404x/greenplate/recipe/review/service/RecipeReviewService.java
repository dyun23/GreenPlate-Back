package com.team404x.greenplate.recipe.review.service;

import com.team404x.greenplate.recipe.model.entity.Recipe;
import com.team404x.greenplate.recipe.review.model.RecipeReview;
import com.team404x.greenplate.recipe.review.model.request.RecipeReviewCreateReq;
import com.team404x.greenplate.recipe.review.model.response.RecipeReviewListRes;
import com.team404x.greenplate.recipe.review.repository.RecipeReviewRepository;
import com.team404x.greenplate.user.model.entity.User;
import com.team404x.greenplate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeReviewService {
    private final UserRepository userRepository;
    private final RecipeReviewRepository recipeReviewRepository;
    public void saveReview(RecipeReviewCreateReq request) {
        recipeReviewRepository.save(RecipeReview.builder()
                        .contents(request.getContents())
                        .rating(request.getRating())
                        .user(User.builder().id(request.getUserId()).build())
                        .recipe(Recipe.builder().id(request.getRecipeId()).build())
                .build());
    }

    public List<RecipeReviewListRes> listReviews(Long recipeId) {
        List<RecipeReview> reviewList = recipeReviewRepository.findAllByRecipeId(recipeId);
        List<RecipeReviewListRes> reviewListRes = new ArrayList<>();
        for (RecipeReview review : reviewList) {
            RecipeReviewListRes result = RecipeReviewListRes.builder()
                    .reviewId(review.getId())
                    .contents(review.getContents())
                    .rating(review.getRating())
                    .userName(userRepository.findById(review.getUser().getId()).get().getName())
                    .build();
            reviewListRes.add(result);
        }
        return reviewListRes;
    }
}
