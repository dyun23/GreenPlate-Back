package com.team404x.greenplate.item.service;

import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.company.service.CompanyService;
import com.team404x.greenplate.item.category.CategoryRepository;
import com.team404x.greenplate.item.category.entity.Category;
import com.team404x.greenplate.item.entity.Item;
import com.team404x.greenplate.item.model.request.ItemCreateReq;
import com.team404x.greenplate.item.repository.ItemRepository;
import com.team404x.greenplate.item.model.request.ItemUpdateReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    public void create(ItemCreateReq itemCreateReq) {
        Category category = categoryRepository.findCategoryBySubCategory(itemCreateReq.getSubCategory());
        Item item = Item.builder()
                .name(itemCreateReq.getName())
                .contents(itemCreateReq.getContents())
                .price(itemCreateReq.getPrice())
                .stock(itemCreateReq.getStock())
                .calorie(itemCreateReq.getCalorie())
                .state(itemCreateReq.getState())
                .imageUrl(itemCreateReq.getImageUrl())
                .discountPrice(itemCreateReq.getDiscountPrice())
                .category(category)
                .company(Company.builder().id(itemCreateReq.getCompanyId()).build())
                .build();
        itemRepository.save(item);
    }
}
