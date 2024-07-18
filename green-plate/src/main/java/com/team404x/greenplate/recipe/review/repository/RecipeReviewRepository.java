package com.team404x.greenplate.recipe.review.repository;

import com.team404x.greenplate.recipe.review.model.RecipeReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeReviewRepository extends JpaRepository<RecipeReview, Long> {
}