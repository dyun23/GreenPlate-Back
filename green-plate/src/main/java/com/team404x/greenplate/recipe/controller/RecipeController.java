package com.team404x.greenplate.recipe.controller;

import com.team404x.greenplate.recipe.model.request.RecipeCreateReq;
import com.team404x.greenplate.recipe.model.request.RecipeUpdateReq;
import com.team404x.greenplate.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
