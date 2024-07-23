package com.team404x.greenplate.recipe.repository;

import com.team404x.greenplate.recipe.item.RecipeItem;
import com.team404x.greenplate.recipe.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface RecipeItemRepository extends JpaRepository<RecipeItem, Long> {
    @Transactional
    @Modifying
    @Query("delete from RecipeItem r where r.recipe = ?1")
    void deleteByRecipe(Recipe recipe);

}