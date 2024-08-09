package com.team404x.greenplate.item.category.repository;

import java.util.List;

import com.team404x.greenplate.item.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryBySubCategory(String subCategory);
    Category findCategoryByMainCategoryAndSubCategory(String mainCategory, String subCategory);
    List<Category> findAllByMainCategory(String mainCategory);
}
