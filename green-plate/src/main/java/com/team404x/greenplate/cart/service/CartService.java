package com.team404x.greenplate.cart.service;

import com.team404x.greenplate.cart.model.entity.Cart;
import com.team404x.greenplate.cart.model.request.CartAddReq;
import com.team404x.greenplate.cart.repository.CartRepository;
import com.team404x.greenplate.item.model.entity.Item;
import com.team404x.greenplate.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
