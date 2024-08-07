package com.team404x.greenplate.item.service;


import java.util.ArrayList;
import java.util.List;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.s3.S3FileUploadSevice;
import com.team404x.greenplate.company.model.entity.Company;
import com.team404x.greenplate.company.repository.CompanyRepository;
import com.team404x.greenplate.item.category.entity.Category;
import com.team404x.greenplate.item.category.repository.CategoryRepository;
import com.team404x.greenplate.item.model.entity.Item;
import com.team404x.greenplate.item.model.request.ItemCreateReq;
import com.team404x.greenplate.item.model.response.*;
import com.team404x.greenplate.item.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.team404x.greenplate.item.model.request.ItemUpdateReq;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;
    private final S3FileUploadSevice s3FileUploadSevice;

    public void create(Long id, ItemCreateReq itemCreateReq, MultipartFile file) {
        Category category = categoryRepository.findCategoryBySubCategory(itemCreateReq.getSubCategory());
        Item item = Item.builder()
                .name(itemCreateReq.getName())
                .contents(itemCreateReq.getContents())
                .price(itemCreateReq.getPrice())
                .stock(itemCreateReq.getStock())
                .calorie(itemCreateReq.getCalorie())
                .state(itemCreateReq.getState())
                .imageUrl(s3FileUploadSevice.upload("item", id, file))
                .discountPrice(itemCreateReq.getDiscountPrice())
                .category(category)
                .company(Company.builder().id(id).build())
                .build();
        itemRepository.save(item);
    }


    public void update(Long id, ItemUpdateReq itemUpdateReq) {
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
                .company(Company.builder().id(id).build())
                .build();
        itemRepository.save(item);
    }

    public Page<ItemRes> list(Pageable pageable) throws Exception {
        Page<Item> itemsPage = itemRepository.findAll(pageable);
        return itemsPage.map(item -> ItemRes.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .calorie(item.getCalorie())
                .imageUrl(item.getImageUrl())
                .discountPrice(item.getDiscountPrice())
                .companyName(item.getCompany().getName())
                .build());
    }

    public List<ItemRes> list(String main, String sub) throws Exception {
        Category category = categoryRepository.findCategoryByMainCategoryAndSubCategory(main, sub);
        List<Item> items = category.getItemList();
        return getItemRes(items);
    }


    public Page<ItemRes> getCategoryPage(String main, String sub, Pageable pageable) throws Exception {
        Category category = categoryRepository.findCategoryByMainCategoryAndSubCategory(main, sub);

        Page<Item> itemsPage = itemRepository.findByCategory(category, pageable);

        Page<ItemRes> itemResPage = itemsPage.map(item -> ItemRes.builder()
            .id(item.getId())
            .name(item.getName())
            .price(item.getPrice())
            .calorie(item.getCalorie())
            .imageUrl(item.getImageUrl())
            .discountPrice(item.getDiscountPrice())
            .companyName(item.getCompany().getName())
            .build());

        return itemResPage;
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


    public List<ItemFindRes> listCompanyItem(Long id) throws Exception{
        Company company = companyRepository.findById(id).orElseThrow();
        List<Item> companyitemlist = company.getItems();
        List<ItemFindRes> itemFindResList = new ArrayList<>();
        for (Item item : companyitemlist) {
            itemFindResList.add(
                    ItemFindRes.builder()
                            .itemId(item.getId())
                            .name(item.getName())
                            .price(item.getPrice())
                            .stock(item.getStock())
                            .state(item.getState())
                            .imageUrl(item.getImageUrl())
                            .discountPrice(item.getDiscountPrice())
                            .build());

        }
        return itemFindResList;
    }


    public List<ItemSearchRes> search(Long id) {
        List<Item> itemSearchList = itemRepository.findAllByCompanyId((id));
        List<ItemSearchRes> itemSearchResList = new ArrayList<>();
        for(Item item : itemSearchList) {
            Category category = null;
            ItemSearchRes responses = ItemSearchRes.builder()
                    .itemId(item.getId())
                    .name(item.getName())
                    .price(item.getPrice())
                    .stock(item.getStock())
                    .state(item.getState())
                    .Imageurl(item.getImageUrl())
                    .discountPrice(item.getDiscountPrice())
                    .mainCategory(item.getCategory().getMainCategory())
                    .subCategory(item.getCategory().getSubCategory())
                    .build();
            itemSearchResList.add(responses);
        }
        return itemSearchResList;
    }
}
