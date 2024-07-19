package com.team404x.greenplate.cart.controller;

import com.team404x.greenplate.cart.model.request.CartAddReq;
import com.team404x.greenplate.cart.model.request.CartUpdateReq;
import com.team404x.greenplate.cart.model.response.CartListRes;
import com.team404x.greenplate.cart.service.CartService;
import com.team404x.greenplate.config.SecuredOperation;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addCart(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CartAddReq request) {
        cartService.createCart(customUserDetails.getId(), request);
        return ResponseEntity.ok("Success");
    }
    @SecuredOperation
    @RequestMapping (method = RequestMethod.POST, value = "/update")
    public ResponseEntity<String> updateQuantity(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CartUpdateReq request) {
        cartService.updateCart(customUserDetails.getId(), request);
        return ResponseEntity.ok("Success");
    }
    @SecuredOperation
    @RequestMapping (method = RequestMethod.GET, value = "/list")
    public ResponseEntity<List<CartListRes>> getCartList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(cartService.getCartList(customUserDetails));
    }
}
