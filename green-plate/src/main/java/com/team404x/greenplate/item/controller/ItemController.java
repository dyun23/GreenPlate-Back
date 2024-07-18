package com.team404x.greenplate.item.controller;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.config.filter.login.CustomUserDetailService;
import com.team404x.greenplate.item.model.request.ItemCreateReq;
import com.team404x.greenplate.item.model.request.ItemUpdateReq;
import com.team404x.greenplate.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @RequestMapping(method= RequestMethod.POST, value="/create")
    public BaseResponse create(@RequestBody ItemCreateReq itemCreateReq){
        itemService.create(itemCreateReq);
        return new BaseResponse(BaseResponseMessage.USER_CREATE_SUCCESS);
        }

    @RequestMapping(method= RequestMethod.POST, value="/update")
    public BaseResponse update(@RequestBody ItemUpdateReq itemUpdateReq) {
        itemService.update(itemUpdateReq);
        return new BaseResponse(BaseResponseMessage.USER_UPDATE_SUCCESS);
    }
//
//    @RequestMapping(method= RequestMethod.POST, value="/readList")
//    public BaseResponse readList(ItemCompanyReadReq itemCompanyReadReq) {
//        itemService.read(itemCompanyReadReq);
//        return new BaseResponse(BaseResponseMessage.USER_READ_LIST_SUCCESS);
//    }
//    @RequestMapping(method= RequestMethod.POST, value="/readDetail")
//    public BaseResponse readDetail(ItemCompanyReadReq itemCompanyReadReq) {
//        itemService.read(itemCompanyReadReq);
//        return new BaseResponse(BaseResponseMessage.USER_READ_DETAIL_SUCCESS);
//    }
//    /*@RequestMapping(method=RequestMethod.POST,value="/search")
//    public BaseResponse search(ItemCompanySearchReq itemCompanySearchReq){
//        itemService.search(itemCompanySearchReq);
//        return new BaseResponse(BaseResponseMessage.USER_SEARCH_SUCCESS);
//    } */



}
