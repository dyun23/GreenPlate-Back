package com.team404x.greenplate.item.category.service;

import com.team404x.greenplate.item.category.entity.Category;
import com.team404x.greenplate.item.category.repository.CategoryRepository;
import com.team404x.greenplate.item.category.response.CategoryListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<CategoryListRes> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, List<String>> categoryMap = new HashMap<>();
        for (Category category : categories) {
            categoryMap
                    .computeIfAbsent(category.getMainCategory(), k -> new ArrayList<>())
                    .add(category.getSubCategory());
        }
        List<CategoryListRes> responses = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
            CategoryListRes response = CategoryListRes.builder()
                    .main(entry.getKey())
                    .subList(entry.getValue())
                    .build();
            responses.add(response);
        }
        return responses;
    }
}
