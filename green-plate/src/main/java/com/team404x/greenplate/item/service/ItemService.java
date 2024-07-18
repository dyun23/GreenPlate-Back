package com.team404x.greenplate.item.service;

import com.team404x.greenplate.item.category.CategoryRepository;
import com.team404x.greenplate.item.category.entity.Category;
import com.team404x.greenplate.item.entity.Item;
import com.team404x.greenplate.item.model.request.ItemCreateReq;
import com.team404x.greenplate.item.model.request.ItemUpdateReq;
import com.team404x.greenplate.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public void create(ItemCreateReq itemCreateReq) {
        Category category = categoryRepository.findCategoryBySubCategory(itemCreateReq.getSub_category());
        Item item = Item.builder()
                .name(itemCreateReq.getName())
                .contents(itemCreateReq.getContents())
                .price(itemCreateReq.getPrice())
                .stock(itemCreateReq.getStock())
                .calorie(itemCreateReq.getCalorie())
                .state(itemCreateReq.getState())
                .imageUrl(itemCreateReq.getImage_url())
                .discountPrice(itemCreateReq.getDiscount_price())
                .category(category)
                .build();
        itemRepository.save(item);
    }
    /*
    public void update(ItemUpdateReq itemUpdateReq) {
        Category category = null;
        Item item = Item.builder()
                .name(itemUpdateReq.getItem_id())
                .contents(itemUpdateReq.getContents())
                .price(itemUpdateReq.getPrice())
                .stock(itemUpdateReq.getStock())
                .calorie(itemUpdateReq.getCalorie())
                .state(itemUpdateReq.getState())
                .imageUrl(itemUpdateReq.getImage_url())
                .discountPrice(itemUpdateReq.getDiscount_price())
                .category(category)
                .build();
    }

    public void delete(ItemDeleteReq itemDeleteReq) {
        Item item = Item.builder()
                .id(itemDeleteReq.getCompany_id())
                .id(itemDeleteReq.getItem_id())
                .build();
    }
    public void read(ItemCompanyReadReq itemCompanyReadReq) {
        Item item = Item.builder()
                .id(itemCompanyReadReq.getCompany_id())
                .build();
    }
    public void readDetail(ItemCompanyReadDetailsReq itemCompanyReadDetailsReq) {
        Item item = Item.builder()
                .id(itemCompanyReadDetailsReq.getCompany_id())
                .id(itemCompanyReadDetailsReq.getItem_id())
                .build();
    }

    /*public void search(ItemCompanySearchReq itemCompanySearchReq) {
        Item item = Item.builder()
                .id(itemCompanySearchReq.getCompany_id())
                .id(itemCompanySearchReq.)

                .build();
    } */
}
