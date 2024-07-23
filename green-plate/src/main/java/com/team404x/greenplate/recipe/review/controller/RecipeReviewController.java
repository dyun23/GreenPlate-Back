package com.team404x.greenplate.recipe.review.controller;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.config.SecuredOperation;
import com.team404x.greenplate.recipe.review.model.request.RecipeReviewCreateReq;
import com.team404x.greenplate.recipe.review.model.response.RecipeReviewListRes;
import com.team404x.greenplate.recipe.review.service.RecipeReviewService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe/review")
@RequiredArgsConstructor
public class RecipeReviewController {
    private final RecipeReviewService recipeReviewService;

    @SecuredOperation
    @Operation(summary = "[유저/사업자] 리뷰 등록 API")
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public BaseResponse createRecipeReview(@RequestBody RecipeReviewCreateReq request) {
        try {
            recipeReviewService.saveReview(request);
        } catch (Exception e) {
            return new BaseResponse<>(BaseResponseMessage.RECIPE_REVIEW_CREATE_FAIL);
        }
        return new BaseResponse<>(BaseResponseMessage.RECIPE_REVIEW_CREATE_SUCCESS);
    }

    @SecuredOperation
    @Operation(summary = "[유저/사업자] 리뷰 전체 목록 불러오기 API")
    @RequestMapping(method = RequestMethod.GET, value = "/{recipeId}/list")
    public BaseResponse<List<RecipeReviewListRes>> getAllReviews(@PathVariable("recipeId") Long recipeId, int page, int size) {
        List<RecipeReviewListRes> result;
        try {
            result = recipeReviewService.listReviews(recipeId, page, size);
        } catch (Exception e) {
            return new BaseResponse<>(BaseResponseMessage.RECIPE_REVIEW_LIST_FAIL);
        }
        return new BaseResponse<>(BaseResponseMessage.RECIPE_REVIEW_LIST_SUCCESS, result);
    }
}
