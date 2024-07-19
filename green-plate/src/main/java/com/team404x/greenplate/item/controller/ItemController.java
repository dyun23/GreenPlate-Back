package com.team404x.greenplate.item.controller;

import java.util.List;

import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import com.team404x.greenplate.item.model.request.ItemCreateReq;
import com.team404x.greenplate.item.model.request.ItemSearchReq;
import com.team404x.greenplate.item.model.request.ItemUpdateReq;
import com.team404x.greenplate.item.model.response.*;
import com.team404x.greenplate.item.service.ItemService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @RequestMapping(method= RequestMethod.POST, value="/create")
    public BaseResponse create(@RequestBody ItemCreateReq itemCreateReq) {
        try {
            itemService.create(itemCreateReq);
            return new BaseResponse(BaseResponseMessage.USER_CREATE_SUCCESS);
        } catch (Exception e) {
             if(itemCreateReq.getCompanyId() == null) {
                return new BaseResponse(BaseResponseMessage.USER_CREATE_FAIL_ISEMPTY);
            } else if (itemCreateReq.getDiscountPrice() > itemCreateReq.getPrice()) {
                return new BaseResponse(BaseResponseMessage.USER_CREATE_FAIL_PRICE);
            } else if (itemCreateReq.getStock() <= 0 || itemCreateReq.getPrice() <= 0) {
                return new BaseResponse(BaseResponseMessage.USER_CREATE_FAIL_QUANTITYANDPRICE);
            } else {
                 return new BaseResponse(BaseResponseMessage.USER_CREATE_SUCCESS);
             }
        }
    }

    @RequestMapping(method= RequestMethod.POST, value="/update")
    public BaseResponse update(@RequestBody ItemUpdateReq itemUpdateReq) {
        try {
            itemService.update(itemUpdateReq);
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


    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public BaseResponse list() {
        try {
            List<ItemRes> itemResList = itemService.list();
            return new BaseResponse(itemResList);
        } catch (Exception e) {
            return new BaseResponse(e.getMessage());
        }
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
//    @RequestMapping(method=RequestMethod.GET,value="/find")
//    public BaseResponse find(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody Long id) {
//        try {
//            itemService.findList(Long id);
//            List<ItemListFindRes> itemListFindResList = itemService.findList(id);
//            return new BaseResponse(BaseResponseMessage.USER_READ_LIST_SUCCESS,itemListFindResList);
//        } catch (Exception e) {
//            if (itemService.findList(id) == null){
//                return new BaseResponse(BaseResponseMessage.USER_READ_LIST_FAIL_NULL); //해당 판매자의 상품이 없다
//            } else {
//                return new BaseResponse(BaseResponseMessage.USER_READ_LIST_FAIL);
//            }
//
//        }
//    }

    @RequestMapping(method=RequestMethod.GET,value="/search")
    public BaseResponse search(@RequestBody ItemSearchReq itemSearchReq) {
        List<ItemSearchRes> itemSearchReqList = itemService.search(itemSearchReq.getCompanyId());
        return new BaseResponse(itemSearchReqList);
    }

}
