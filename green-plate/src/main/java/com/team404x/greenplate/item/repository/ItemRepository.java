package com.team404x.greenplate.item.repository;

import com.team404x.greenplate.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT SUM(p.calorie) FROM Item p WHERE p.id in :itemId")
    Integer getCalorieSum(Long[] itemId);

    @Query("select i from Item i inner join i.recipeItems recipeItems where recipeItems.recipe.id = ?1")
    List<Item> findByRecipeItemsRecipeId(Long id);

}