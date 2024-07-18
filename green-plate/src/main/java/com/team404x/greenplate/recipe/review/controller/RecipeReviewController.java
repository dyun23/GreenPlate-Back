package com.team404x.greenplate.recipe.review.controller;

import com.team404x.greenplate.recipe.review.model.request.RecipeReviewCreateReq;
import com.team404x.greenplate.recipe.review.service.RecipeReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
