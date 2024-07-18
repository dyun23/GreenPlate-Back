package com.team404x.greenplate.item.category;

import com.team404x.greenplate.item.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
   Category findCategoryBySubCategory(String subCategory);
}
