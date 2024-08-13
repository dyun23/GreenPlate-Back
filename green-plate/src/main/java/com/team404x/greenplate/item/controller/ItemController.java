package com.team404x.greenplate.item.controller;

import java.util.List;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.item.model.request.ItemCreateReq;
import com.team404x.greenplate.item.model.request.ItemSearchReq;
import com.team404x.greenplate.item.model.request.ItemUpdateReq;
import com.team404x.greenplate.item.model.response.*;
import com.team404x.greenplate.item.service.ItemService;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import com.team404x.greenplate.config.SecuredOperation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @SecuredOperation
    @Operation(summary = "[사업자] 상품 등록을 위한 API")
    @RequestMapping(method= RequestMethod.POST, value="/create")
    public BaseResponse create(@AuthenticationPrincipal CustomUserDetails company, @RequestPart ItemCreateReq request,
        @RequestPart MultipartFile file) {
        try {
            itemService.create(company.getId(), request, file);
            return new BaseResponse(BaseResponseMessage.USER_CREATE_SUCCESS);
        } catch (Exception e) {
             if (request.getDiscountPrice() > request.getPrice()) {
                return new BaseResponse(BaseResponseMessage.USER_CREATE_FAIL_PRICE);
            } else if (request.getStock() <= 0 || request.getPrice() <= 0) {
                return new BaseResponse(BaseResponseMessage.USER_CREATE_FAIL_QUANTITYANDPRICE);
            } else {
                 return new BaseResponse(BaseResponseMessage.USER_CREATE_FAIL);
             }
        }
    }

    @SecuredOperation
    @Operation(summary = "[사업자] 상품 수정을 위한 API")
    @RequestMapping(method= RequestMethod.POST, value="/update")
    public BaseResponse update(@AuthenticationPrincipal CustomUserDetails company, @RequestBody ItemUpdateReq itemUpdateReq) {
        try {
            itemService.update(company.getId(), itemUpdateReq);
            return new BaseResponse(BaseResponseMessage.USER_UPDATE_SUCCESS);
        } catch (Exception e) {
            if (itemUpdateReq.getStock() < 0 || itemUpdateReq.getPrice() < 0 ) {
                return new BaseResponse(BaseResponseMessage.USER_UPDATE_FAIL_ISEMPTY); // 비워져있는 값이 있음
            } else if (itemUpdateReq.getItemId() ==null) {
                return new BaseResponse(BaseResponseMessage.USER_UPDATE_FAIL_NULL); // 존재하지 않은 상품
            } else if(itemUpdateReq.getDiscountPrice() < itemUpdateReq.getPrice()){
                return new BaseResponse(BaseResponseMessage.USER_UPDATE_FAIL_PRICE);// 할인금액이 정가보다 큼
            } else if(itemUpdateReq.getStock() <= 0 || itemUpdateReq.getPrice() <= 0){
                return new BaseResponse(BaseResponseMessage.USER_UPDATE_FAIL_QUANTITYANDPRICE); //수량이나 금액이 0 이하입니다
            } else {
                return new BaseResponse(BaseResponseMessage.USER_UPDATE_FAIL);
            }
        }
    }

    @SecuredOperation
    @Operation(summary = "[사업자] 상품 수정을 위한 API")
    @RequestMapping(method= RequestMethod.GET, value="/update/simple")
    public BaseResponse updateItem(@AuthenticationPrincipal CustomUserDetails company, Long id, int price, int stock) {
        try {
            itemService.update(company.getId(), id, price, stock);
            return new BaseResponse(BaseResponseMessage.USER_UPDATE_SUCCESS);
        } catch (Exception e) {
                return new BaseResponse(BaseResponseMessage.USER_UPDATE_FAIL);
            }
    }

    /*
    TODO
    연관관계 때문에 제대로 삭제 안됨 -> 상품 상세 내역을 지워야 하는데.. 애매하다..?
    * */
    @SecuredOperation
    @Operation(summary = "[사업자] 상품 삭제를 위한 API")
    @RequestMapping(method= RequestMethod.GET, value="/delete")
    public BaseResponse delete(@AuthenticationPrincipal CustomUserDetails company, Long id) {
        try {
            if (company.getId() == null)
                throw new Exception();
            itemService.delete(id);
            return new BaseResponse(BaseResponseMessage.USER_UPDATE_SUCCESS);
        } catch (Exception e) {
            return new BaseResponse(BaseResponseMessage.USER_UPDATE_FAIL);
        }
    }


    @Operation(summary = "[전체] 상품 전체 목록 조회를 위한 API")
    @GetMapping("/list")
    public BaseResponse<Page<ItemRes>> list(
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            Page<ItemRes> itemResPage = itemService.list(pageable);
            return new BaseResponse<>(BaseResponseMessage.ITEM_LIST_SUCCESS, itemResPage);
        } catch (Exception e) {
            return new BaseResponse<>(BaseResponseMessage.ITEM_LIST_FAIL, null);
        }
    }

    @Operation(summary = "[전체] 카테고리에 해당하는 상품 목록을 조회하는 API")
    @RequestMapping(method = RequestMethod.GET, value = "/list/category")
    public BaseResponse<List<ItemRes>> list(String main, String sub) {
        try {
            List<ItemRes> itemResList = itemService.list(main, sub);
            return new BaseResponse(BaseResponseMessage.ITEM_LIST_CATEGORY_SUCCESS, itemResList);
        } catch (Exception e) {
            return new BaseResponse(BaseResponseMessage.ITEM_LIST_CATEGORY_FAIL);
        }
    }

    @Operation(summary = "[전체] 카테고리에 해당하는 상품 목록을 조회하는 API")
    @RequestMapping(method = RequestMethod.GET, value = "/category")
    public BaseResponse<Page<ItemRes>> getCategoryPage(String main, String sub, @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            Page<ItemRes> itemResList = itemService.getCategoryPage(main, sub, pageable);
            return new BaseResponse(BaseResponseMessage.ITEM_LIST_CATEGORY_SUCCESS, itemResList);
        } catch (Exception e) {
            return new BaseResponse(BaseResponseMessage.ITEM_LIST_CATEGORY_FAIL);
        }
    }

    @Operation(summary = "[전체] 상품 이름으로 목록을 조회하는 API")
    @RequestMapping(method = RequestMethod.GET, value = "/list/{name}")
    public BaseResponse list(@PathVariable String name) {
        try {
            List<ItemRes> itemResList = itemService.list(name);
            return itemResList.size() == 0 ?
                new BaseResponse(BaseResponseMessage.ITEM_LIST_NAME_FAIL_NOT_FOUND) :
                new BaseResponse(BaseResponseMessage.ITEM_LIST_NAME_SUCCESS, itemResList);
        } catch (Exception e) {
            return new BaseResponse(BaseResponseMessage.ITEM_LIST_NAME_FAIL);
        }
    }

    @Operation(summary = "[전체] 상품 이름으로 목록을 조회하는 API")
    @RequestMapping(method = RequestMethod.GET, value = "/search/word")
    public BaseResponse list(String name, @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            Page<ItemRes> itemResList = itemService.list(name, pageable);
            return itemResList.getSize() == 0 ?
                new BaseResponse(BaseResponseMessage.ITEM_LIST_NAME_FAIL_NOT_FOUND) :
                new BaseResponse(BaseResponseMessage.ITEM_LIST_NAME_SUCCESS, itemResList);
        } catch (Exception e) {
            return new BaseResponse(BaseResponseMessage.ITEM_LIST_NAME_FAIL);
        }
    }


    @Operation(summary = "[전체] 상품 상세 정보를 조회하기 위한 API")
	@RequestMapping(method = RequestMethod.GET, value = "/details")
    public BaseResponse list(Long id) {
        try {
            ItemDetailsRes itemDetailsRes = itemService.details(id);
            return new BaseResponse(BaseResponseMessage.ITEM_DETAILS_SUCCESS, itemDetailsRes);
        } catch (Exception e) {
            return new BaseResponse(BaseResponseMessage.ITEM_DETAILS_FAIL);
        }
    }
    @Operation(summary = "[사업자] 상품을 검색하기 위한 API")
    @RequestMapping(method=RequestMethod.GET,value="/company")
    public BaseResponse listCompanyItem(@AuthenticationPrincipal CustomUserDetails company){
        try {
           List<ItemFindRes> result = itemService.listCompanyItem(company.getId());
           return new BaseResponse(BaseResponseMessage.USER_READ_LIST_SUCCESS,result);
        } catch (Exception e) {
            return new BaseResponse(BaseResponseMessage.USER_READ_LIST_FAIL);
        }

    }

    @Operation(summary = "[전체] 상품 목록 정보를 조회하기 위한 API")
    @RequestMapping(method=RequestMethod.POST,value="/search")
    public BaseResponse search(@RequestBody ItemSearchReq itemSearchReq) {
        try {
            List<ItemSearchRes> itemSearchReqList = itemService.search(itemSearchReq.getCompanyId());
            return new BaseResponse(BaseResponseMessage.USER_SEARCH_SUCCESS,itemSearchReqList);
        } catch (Exception e) {
            if(itemSearchReq.getSearch() == null){ // 검색어에 맞는 상품이 없습니다
                return new BaseResponse(BaseResponseMessage.USER_SEARCH_FAIL_NOTFOUND);
            } else{
                return new BaseResponse(BaseResponseMessage.USER_SEARCH_FAIL);
            }
        }
    }


}
