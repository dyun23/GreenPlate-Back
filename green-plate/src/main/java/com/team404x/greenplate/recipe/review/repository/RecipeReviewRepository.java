package com.team404x.greenplate.recipe.review.repository;

import com.team404x.greenplate.recipe.review.model.RecipeReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RecipeReviewRepository extends JpaRepository<RecipeReview, Long> {
//    List<RecipeReview> findAllByRecipeId(Long recipeId);

    @Query("SELECT rr FROM RecipeReview rr JOIN FETCH rr.user WHERE rr.recipe.id = :recipeId")
    Page<RecipeReview> findByRecipeIdWithUser(Long recipeId, Pageable pageable);
}