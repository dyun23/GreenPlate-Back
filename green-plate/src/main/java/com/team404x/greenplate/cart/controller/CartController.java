package com.team404x.greenplate.cart.controller;

import com.team404x.greenplate.cart.model.request.CartAddReq;
import com.team404x.greenplate.cart.service.CartService;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<String> addCart(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CartAddReq request) {
        cartService.createCart(customUserDetails.getId(),request);
        return ResponseEntity.ok("Success");
    }
}
