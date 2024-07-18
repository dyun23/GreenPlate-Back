package com.team404x.greenplate.item.controller;

import java.util.List;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.item.model.request.ItemCreateReq;
import com.team404x.greenplate.item.model.response.ItemRes;
import com.team404x.greenplate.item.service.ItemService;
import com.team404x.greenplate.item.model.request.ItemUpdateReq;
import lombok.RequiredArgsConstructor;
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

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public BaseResponse list() {
        List<ItemRes> itemResList = itemService.list();
        return new BaseResponse(itemResList);
    }

}
