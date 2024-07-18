package com.team404x.greenplate.item.service;


import java.util.ArrayList;
import java.util.List;

import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.item.category.entity.Category;
import com.team404x.greenplate.item.category.repository.CategoryRepository;
import com.team404x.greenplate.item.model.entity.Item;
import com.team404x.greenplate.item.model.request.ItemCreateReq;
import com.team404x.greenplate.item.model.response.ItemDetailsRes;
import com.team404x.greenplate.item.model.response.ItemRes;
import com.team404x.greenplate.item.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.team404x.greenplate.item.model.request.ItemUpdateReq;

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


    public void update(ItemUpdateReq itemUpdateReq) {
        Category category = categoryRepository.findCategoryByMainCategoryAndSubCategory(itemUpdateReq.getMainCategory(), itemUpdateReq.getSubCategory());
        Item item = Item.builder()
                .id(itemUpdateReq.getItemId())
                .name(itemUpdateReq.getName())
                .contents(itemUpdateReq.getContents())
                .price(itemUpdateReq.getPrice())
                .stock(itemUpdateReq.getStock())
                .calorie(itemUpdateReq.getCalorie())
                .state(itemUpdateReq.getState())
                .imageUrl(itemUpdateReq.getImageUrl())
                .discountPrice(itemUpdateReq.getDiscountPrice())
                .category(category)
                .company(Company.builder().id(itemUpdateReq.getCompanyId()).build())
                .build();
        itemRepository.save(item);
    }
    public List<ItemRes> list() throws Exception {
        List<Item> items = itemRepository.findAll();
        return getItemRes(items);
    }

    public List<ItemRes> list(String main, String sub) throws Exception {
        Category category = categoryRepository.findCategoryByMainCategoryAndSubCategory(main, sub);
        List<Item> items = category.getItemList();
        return getItemRes(items);
    }

    public List<ItemRes> list(String name) throws Exception {
        List<Item> items = itemRepository.findByNameContaining(name);
        return getItemRes(items);
    }

    public ItemDetailsRes details(Long id) throws Exception {
        Item item = itemRepository.findById(id).orElseThrow();
        return ItemDetailsRes.builder()
            .id(item.getId())
            .name(item.getName())
            .price(item.getPrice())
            .calorie(item.getCalorie())
            .imageUrl(item.getImageUrl())
            .discountPrice(item.getDiscountPrice())
            .stock(item.getStock())
            .contents(item.getContents())
            .companyName(item.getCompany().getName())
            .build();
    }


    private List<ItemRes> getItemRes(List<Item> items) throws Exception {
        List<ItemRes> itemResList = new ArrayList<>();
        for (Item item : items) {
            itemResList.add(
                ItemRes.builder()
                    .id(item.getId())
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
