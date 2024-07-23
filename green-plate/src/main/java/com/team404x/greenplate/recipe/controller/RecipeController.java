package com.team404x.greenplate.recipe.controller;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.config.SecuredOperation;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import com.team404x.greenplate.recipe.model.request.RecipeCreateReq;
import com.team404x.greenplate.recipe.model.request.RecipeUpdateReq;
import com.team404x.greenplate.recipe.model.response.RecipeDetailsRes;
import com.team404x.greenplate.recipe.model.response.RecipeListRes;
import com.team404x.greenplate.recipe.service.RecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    @SecuredOperation
    @Operation(summary = "[유저/사업자] 레시피 등록 API")
    @RequestMapping(method = RequestMethod.POST, value = "/create", produces = "application/json", consumes = "multipart/form-data")
    public BaseResponse<String> create(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                       @Parameter(name = "request") @ModelAttribute RecipeCreateReq request,
                                       @Parameter(name = "file") @RequestParam(value = "file") MultipartFile file) {
        try {
            recipeService.createRecipe(customUserDetails, request, file);
        }
        catch (Exception e) {
            if (e.getMessage().equals("필수값")) {
                return new BaseResponse<>(BaseResponseMessage.RECIPE_CREATE_FAIL_HAS_NULL);
            }
        }
        return new BaseResponse<>(BaseResponseMessage.RECIPE_CREATE_SUCCESS);
    }

    @SecuredOperation
    @Operation(summary = "[유저/사업자] 레시피 수정 API")
    @RequestMapping(method = RequestMethod.POST, value = "/update", produces = "application/json", consumes = "multipart/form-data")
    public BaseResponse<String> update(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                       @Parameter(name = "request") @ModelAttribute RecipeUpdateReq request,
                                       @Parameter(name = "file") @RequestParam(value = "file") MultipartFile file) {
        try {
            recipeService.updateRecipe(customUserDetails, request, file);
        } catch (Exception e) {
            if (e.getMessage().equals("본인아님")) {
                return new BaseResponse<>(BaseResponseMessage.RECIPE_UPDATE_FAIL_NOT_MEMBER);
            } else if (e.getMessage().equals("필수값")) {
                return new BaseResponse<>(BaseResponseMessage.RECIPE_UPDATE_FAIL_HAS_NULL);
            }
            return new BaseResponse<>(BaseResponseMessage.RECIPE_UPDATE_FAIL);
        }
        return new BaseResponse<>(BaseResponseMessage.RECIPE_UPDATE_SUCCESS);
    }

    @Operation(summary = "[전체] 레시피 목록 조회 API")
    @RequestMapping(method = RequestMethod.GET, value = "/list/{page}")
    public BaseResponse<List<RecipeListRes>> list(@PathVariable("page")int page, int size, String search) {
        List<RecipeListRes> result;
        try{
            result = recipeService.listRecipes(page, size, search);
        } catch (Exception e) {
            return new BaseResponse<>(BaseResponseMessage.RECIPE_LIST_FAIL);
        }
        return new BaseResponse<>(BaseResponseMessage.RECIPE_LIST_SUCCESS, result);
    }

    @Operation(summary = "[전체] 레시피 상세 정보 조회 API")
    @RequestMapping(method = RequestMethod.GET, value = "/details")
    public BaseResponse<RecipeDetailsRes> details(Long recipeId) {
        RecipeDetailsRes result;
        try {
            result = recipeService.readRecipe(recipeId);
        } catch (Exception e) {
            return new BaseResponse<>(BaseResponseMessage.RECIPE_DETAILS_FAIL);
        }
        return new BaseResponse<>(BaseResponseMessage.RECIPE_DETAILS_SUCCESS, result);
    }
}
