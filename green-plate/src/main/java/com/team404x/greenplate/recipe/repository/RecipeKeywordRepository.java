package com.team404x.greenplate.recipe.repository;

import com.team404x.greenplate.recipe.keyword.entity.RecipeKeyword;
import com.team404x.greenplate.recipe.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface RecipeKeywordRepository extends JpaRepository<RecipeKeyword, Long> {
    @Transactional
    @Modifying
    @Query("delete from RecipeKeyword r where r.recipe = ?1")
    void deleteByRecipe(Recipe recipe);
//
//    @Query("select rk from RecipeKeyword rk join fetch rk.recipe join fetch rk.keyword where rk.recipe=:recipe")
//    List<RecipeKeyword> findByRecipe(Recipe recipe);
}