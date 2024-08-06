package com.team404x.greenplate.item.category.controller;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.item.category.response.CategoryListRes;
import com.team404x.greenplate.item.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/list")
    public BaseResponse<List<CategoryListRes>> list() {
        return new BaseResponse<>(BaseResponseMessage.CATEGORY_LIST_SUCCESS, categoryService.getAllCategories());
    }
}
