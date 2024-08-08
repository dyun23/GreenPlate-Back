package com.team404x.greenplate.cart.controller;

import com.team404x.greenplate.cart.model.request.CartAddReq;
import com.team404x.greenplate.cart.model.request.CartDeleteListReq;
import com.team404x.greenplate.cart.model.request.CartUpdateReq;
import com.team404x.greenplate.cart.model.response.CartListRes;
import com.team404x.greenplate.cart.service.CartService;
import com.team404x.greenplate.common.BaseResponse;
import com.team404x.greenplate.common.BaseResponseMessage;
import com.team404x.greenplate.config.SecuredOperation;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @SecuredOperation
    @Operation(summary = "[유저] 장바구니에 상품 담기 API")
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public BaseResponse<String> addCart(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CartAddReq request) {
        try {
            cartService.createCart(customUserDetails.getId(), request);
        } catch (Exception e) {
            return new BaseResponse<>(BaseResponseMessage.CART_ADD_FAIL);
        }
        return new BaseResponse<>(BaseResponseMessage.CART_ADD_SUCCESS);
    }
    @SecuredOperation
    @Operation(summary = "[유저] 장바구니에 상품 수량 수정 API")
    @RequestMapping (method = RequestMethod.POST, value = "/update")
    public BaseResponse<String> updateQuantity(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CartUpdateReq request) {
        try {
            cartService.updateCart(customUserDetails.getId(), request);
        } catch (Exception e) {
            if (e.getMessage().equals("없음")){
                return new BaseResponse<>(BaseResponseMessage.CART_UPDATE_FAIL_NULL);
            } else if (e.getMessage().equals("본인아님")){
                return new BaseResponse<>(BaseResponseMessage.CART_UPDATE_FAIL_NOT_USER);
            }
            return new BaseResponse<>(BaseResponseMessage.CART_UPDATE_FAIL);
        }
        return new BaseResponse<>(BaseResponseMessage.CART_UPDATE_SUCCESS);
    }
    @SecuredOperation
    @Operation(summary = "[유저] 장바구니에 목록 불러오기 API")
    @RequestMapping (method = RequestMethod.GET, value = "/list")
    public BaseResponse<List<CartListRes>> getCartList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<CartListRes> result;
        try {
            result = cartService.getCartList(customUserDetails);
        } catch (Exception e) {
            return new BaseResponse<>(BaseResponseMessage.CART_LIST_FAIL);
        }
        return new BaseResponse<>(BaseResponseMessage.CART_LIST_SUCCESS,result);
    }
    @SecuredOperation
    @Operation(summary = "[유저] 장바구니에 상품 삭제하기 API")
    @DeleteMapping("/{id}/delete")
    public BaseResponse<String> deleteCart(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long id) {
        try {
            cartService.deleteItemFromCart(customUserDetails.getId(), id);
            return new BaseResponse<>(BaseResponseMessage.CART_DELETE_SUCCESS);
        } catch (Exception e) {
            if (e.getMessage().equals("본인아님")){
                return new BaseResponse<>(BaseResponseMessage.CART_DELETE_FAIL_NOT_USER);
            }
            return new BaseResponse<>(BaseResponseMessage.CART_DELETE_FAIL);
        }
    }
    @SecuredOperation
    @Operation(summary = "[유저] 장바구니에 여러 상품 삭제하기 API")
    @RequestMapping (method = RequestMethod.POST, value = "/delete/list")
    public BaseResponse<String> deleteCartList(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CartDeleteListReq request) {
        try {
            cartService.deleteListFromCart(customUserDetails.getId(), request);
            return new BaseResponse<>(BaseResponseMessage.CART_DELETE_SUCCESS);
        } catch (Exception e) {
            if (e.getMessage().equals("본인아님")){
                return new BaseResponse<>(BaseResponseMessage.CART_DELETE_FAIL_NOT_USER);
            } else if (e.getMessage().equals("장바구니 항목이 존재 안함")) {
                return new BaseResponse<>(BaseResponseMessage.CART_DELETE_FAIL_NOT_USER);
            }
            return new BaseResponse<>(BaseResponseMessage.CART_DELETE_FAIL);
        }
    }
}
