package com.team404x.greenplate.cart.service;

import com.team404x.greenplate.cart.model.entity.Cart;
import com.team404x.greenplate.cart.model.request.CartAddReq;
import com.team404x.greenplate.cart.model.request.CartUpdateReq;
import com.team404x.greenplate.cart.model.response.CartListRes;
import com.team404x.greenplate.cart.repository.CartRepository;
import com.team404x.greenplate.config.filter.login.CustomUserDetails;
import com.team404x.greenplate.item.model.entity.Item;
import com.team404x.greenplate.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    public void createCart(Long id, CartAddReq request) {
        cartRepository.save(
                Cart.builder()
                        .user(User.builder().id(id).build())
                        .item(Item.builder().id(request.getItemId()).build())
                        .quantity(request.getQuantity())
                        .build());
    }

    public void updateCart(Long id, CartUpdateReq request) {
        Optional<Cart> result = cartRepository.findById(request.getCartId());
        if (result.isPresent()) {
            Cart cart = result.get();
            if (cart.getUser().getId().equals(id)) {
                cartRepository.save(
                        Cart.builder()
                                .id(cart.getId())
                                .user(cart.getUser())
                                .item(cart.getItem())
                                .quantity(request.getQuantity())
                                .build()
                );
            }
        }
    }

    public List<CartListRes> getCartList(CustomUserDetails customUserDetails) {
        List<Cart> cartList = cartRepository.findAllByUserId(customUserDetails.getId());
        List<CartListRes> cartListRes = new ArrayList<>();
        for (Cart cart : cartList) {
            CartListRes res = CartListRes.builder()
                    .cartId(cart.getId())
                    .itemId(cart.getItem().getId())
                    .itemName(cart.getItem().getName())
                    .discountPrice(cart.getItem().getDiscountPrice())
                    .quantity(cart.getQuantity())
                    .build();
            cartListRes.add(res);
        }
        return cartListRes;
    }
}
