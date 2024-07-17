package com.team404x.greenplate.recipe.repository;

import com.team404x.greenplate.recipe.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("select r from Recipe r where r.title like concat('%', ?1, '%')")
    List<Recipe> findByTitleContains(String title);
}