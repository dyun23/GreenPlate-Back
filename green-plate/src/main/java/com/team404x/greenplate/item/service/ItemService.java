package com.team404x.greenplate.item.service;

import java.util.ArrayList;
import java.util.List;

import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.item.category.entity.Category;
import com.team404x.greenplate.item.category.repository.CategoryRepository;
import com.team404x.greenplate.item.model.entity.Item;
import com.team404x.greenplate.item.model.request.ItemCreateReq;
import com.team404x.greenplate.item.model.response.ItemRes;
import com.team404x.greenplate.item.repository.ItemRepository;
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

    public List<ItemRes> list() {
        List<Item> items = itemRepository.findAll();
        List<ItemRes> itemResList = new ArrayList<>();
        for (Item item : items) {
            itemResList.add(
                ItemRes.builder()
                    .name(item.getName())
                    .price(item.getPrice())
                    .calorie(item.getCalorie())
                    .imageUrl(item.getImageUrl())
                    .discountPrice(item.getDiscountPrice())
                    .companyName(item.getCompany().getName())
                    .build()
            );
        }
        return itemResList;
    }
}
