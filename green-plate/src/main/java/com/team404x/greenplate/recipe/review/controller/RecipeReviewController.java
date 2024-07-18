package com.team404x.greenplate.recipe.review.controller;

import com.team404x.greenplate.recipe.review.model.request.RecipeReviewCreateReq;
import com.team404x.greenplate.recipe.review.model.response.RecipeReviewListRes;
import com.team404x.greenplate.recipe.review.service.RecipeReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe/review")
@RequiredArgsConstructor
public class RecipeReviewController {
    private final RecipeReviewService recipeReviewService;
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<String> createRecipeReview(@RequestBody RecipeReviewCreateReq request) {
        recipeReviewService.saveReview(request);
        return ResponseEntity.ok("Success");
    }
    @RequestMapping(method = RequestMethod.GET, value = "/{recipeId}/list")
    public ResponseEntity<List<RecipeReviewListRes>> getAllReviews(@PathVariable("recipeId") Long recipeId) {
        return ResponseEntity.ok(recipeReviewService.listReviews(recipeId));
    }
}
