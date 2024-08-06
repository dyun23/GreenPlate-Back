package com.team404x.greenplate.recipe.repository;

import com.team404x.greenplate.recipe.model.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.user LEFT JOIN FETCH r.company LEFT JOIN FETCH r.recipeKeywords rk LEFT JOIN FETCH rk.keyword")

//    List<Recipe> findAllWithKeywordsAndUserCompany();
    Page<Recipe> findAllWithKeywordsAndUserCompany(Pageable pageable);

    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.user LEFT JOIN FETCH r.company LEFT JOIN FETCH r.recipeKeywords rk LEFT JOIN FETCH rk.keyword WHERE r.title LIKE %:search%")
    Page<Recipe> findByTitleContainsWithKeywords(Pageable pageable, @Param("search") String search);

//    @Query("select r from Recipe r where r.title like concat('%', ?1, '%')")
    List<Recipe> findByTitleContains(String title);


}