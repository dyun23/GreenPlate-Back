package com.team404x.greenplate.item.controller;

import java.util.List;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import com.team404x.greenplate.item.model.request.ItemCreateReq;
import com.team404x.greenplate.item.model.request.ItemUpdateReq;
import com.team404x.greenplate.item.model.response.ItemCompanyListRes;
import com.team404x.greenplate.item.model.response.ItemDetailsRes;
import com.team404x.greenplate.item.model.response.ItemRes;
import com.team404x.greenplate.item.service.ItemService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public BaseResponse create(@RequestBody ItemCreateReq itemCreateReq) {
        itemService.create(itemCreateReq);
        return new BaseResponse(BaseResponseMessage.USER_CREATE_SUCCESS);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public BaseResponse update(@RequestBody ItemUpdateReq itemUpdateReq) {
        itemService.update(itemUpdateReq);
        return new BaseResponse(BaseResponseMessage.USER_UPDATE_SUCCESS);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public BaseResponse list() {
        List<ItemRes> itemResList = itemService.list();
        return new BaseResponse(itemResList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list/category")
    public BaseResponse list(String main, String sub) {
        List<ItemRes> itemResList = itemService.list(main, sub);
        return new BaseResponse(itemResList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list/{name}")
    public BaseResponse list(@PathVariable String name) {
        List<ItemRes> itemResList = itemService.list(name);
        return new BaseResponse(itemResList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/details")
    public BaseResponse list(Long id) {
        ItemDetailsRes itemDetailsRes = itemService.details(id);
        return new BaseResponse(itemDetailsRes);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/companylistAll")
    public BaseResponse companylistAll(@AuthenticationPrincipal CustomUserDetails customUserDetails, ItemCompanyListRes itemCompanyRes) {
        List<ItemCompanyListRes> itemCompanyListRes = itemService.getItemCompanyList(customUserDetails.getId());
        return new BaseResponse(itemCompanyListRes);

    }



}
