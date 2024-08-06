package com.team404x.greenplate.item.category.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CategoryListRes {
    private String main;
    private List<String> subList;
}
