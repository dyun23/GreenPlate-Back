package com.team404x.greenplate.item.controller;

import java.util.List;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.config.SecuredOperation;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import com.team404x.greenplate.item.model.request.ItemCreateReq;
import com.team404x.greenplate.item.model.request.ItemUpdateReq;
import com.team404x.greenplate.item.model.response.ItemDetailsRes;
import com.team404x.greenplate.item.model.response.ItemRes;
import com.team404x.greenplate.item.service.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @SecuredOperation
    @Operation(summary = "[사업자] 상품 등록을 위한 API")
    @RequestMapping(method= RequestMethod.POST, value="/create")
    public BaseResponse create(@AuthenticationPrincipal CustomUserDetails company,
        @RequestBody ItemCreateReq itemCreateReq) {
        try {
            itemService.create(company.getId(), itemCreateReq);
            return new BaseResponse(BaseResponseMessage.USER_CREATE_SUCCESS);
        }
        catch (Exception e) {
            return new BaseResponse(BaseResponseMessage.USER_CREATE_FAIL);
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
            return new BaseResponse(BaseResponseMessage.USER_UPDATE_FAIL);
        }
    }

    @Operation(summary = "[전체] 상품 전체 목록 조회를 위한 API")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public BaseResponse list() {
        try {
            List<ItemRes> itemResList = itemService.list();
            return new BaseResponse(BaseResponseMessage.ITEM_LIST_SUCCESS, itemResList);
        } catch (Exception e) {
            return new BaseResponse(BaseResponseMessage.ITEM_LIST_FAIL);
        }
    }

    @Operation(summary = "[전체] 카테고리에 해당하는 상품 목록을 조회하는 API")
    @RequestMapping(method = RequestMethod.GET, value = "/list/category")
    public BaseResponse list(String main, String sub) {
        try {
            List<ItemRes> itemResList = itemService.list(main, sub);
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
}
