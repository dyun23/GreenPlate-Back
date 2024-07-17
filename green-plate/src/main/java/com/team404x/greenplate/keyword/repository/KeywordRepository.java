package com.team404x.greenplate.keyword.repository;

import com.team404x.greenplate.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
  Keyword findByName(String name);

  @Query("select k.name from Keyword k inner join k.recipeKeywords recipeKeywords where recipeKeywords.recipe.id = ?1")
  List<String> findByRecipeKeywordsRecipeId(Long id);
}