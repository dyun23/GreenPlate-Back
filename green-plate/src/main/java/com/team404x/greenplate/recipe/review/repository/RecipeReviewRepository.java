package com.team404x.greenplate.recipe.review.repository;

import com.team404x.greenplate.recipe.review.model.RecipeReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeReviewRepository extends JpaRepository<RecipeReview, Long> {
    List<RecipeReview> findAllByRecipeId(Long recipeId);
}