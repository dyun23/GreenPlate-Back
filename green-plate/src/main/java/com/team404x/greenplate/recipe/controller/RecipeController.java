package com.team404x.greenplate.recipe.controller;

import com.team404x.greenplate.recipe.model.request.RecipeCreateReq;
import com.team404x.greenplate.recipe.model.request.RecipeUpdateReq;
import com.team404x.greenplate.recipe.model.response.RecipeDetailsRes;
import com.team404x.greenplate.recipe.model.response.RecipeListRes;
import com.team404x.greenplate.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<String> create(@RequestPart RecipeCreateReq request, @RequestPart MultipartFile file) {
        recipeService.createRecipe(request, file);
        return ResponseEntity.ok("okay");
    }
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ResponseEntity<String> update(@RequestPart RecipeUpdateReq request, @RequestPart MultipartFile file) {
        recipeService.updateRecipe(request, file);
        return ResponseEntity.ok("okay");
    }
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity<List<RecipeListRes>> list(String search) {
        return ResponseEntity.ok(recipeService.listRecipes(search));
    }
    @RequestMapping(method = RequestMethod.GET, value = "/details")
    public ResponseEntity<RecipeDetailsRes> details(Long recipeId) {
        return ResponseEntity.ok(recipeService.readRecipe(recipeId));
    }
}