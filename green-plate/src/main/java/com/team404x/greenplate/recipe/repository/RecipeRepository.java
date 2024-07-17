package com.team404x.greenplate.recipe.repository;

import com.team404x.greenplate.recipe.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}