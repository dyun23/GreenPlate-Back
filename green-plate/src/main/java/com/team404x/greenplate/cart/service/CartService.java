package com.team404x.greenplate.cart.service;

import com.team404x.greenplate.cart.model.entity.Cart;
import com.team404x.greenplate.cart.model.request.CartAddReq;
import com.team404x.greenplate.cart.model.request.CartDeleteListReq;
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
        List<Cart> cartList = cartRepository.findByUserWithItems(User.builder().id(id).build());
        for(Cart Onecart : cartList){
            if(Onecart.getItem().getId().equals(request.getItemId()))
            {
                Optional<Cart> cart = cartRepository.findById(Onecart.getId());
                cart.get().setQuantity(Onecart.getQuantity()+1);
                cartRepository.save(cart.get());
                return;
            }
        }

        cartRepository.save(
                Cart.builder()
                        .user(User.builder().id(id).build())
                        .item(Item.builder().id(request.getItemId()).build())
                        .quantity(request.getQuantity())
                        .build());
    }

    public void updateCart(Long id, CartUpdateReq request) throws Exception {
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
            } else {
                throw new Exception("본인아님");
            }
        } else {
            throw new Exception("없음");
        }
    }

    public List<CartListRes> getCartList(CustomUserDetails customUserDetails) {
        List<Cart> cartList = cartRepository.findByUserWithItems(User.builder().id(customUserDetails.getId()).build());
        List<CartListRes> cartListRes = new ArrayList<>();
        for (Cart cart : cartList) {
            CartListRes res = CartListRes.builder()
                    .cartId(cart.getId())
                    .itemId(cart.getItem().getId())
                    .itemName(cart.getItem().getName())
                    .price(cart.getItem().getPrice())
                    .discountPrice(cart.getItem().getDiscountPrice())
                    .quantity(cart.getQuantity())
                    .imageUrl(cart.getItem().getImageUrl())
                    .build();
            cartListRes.add(res);
        }
        return cartListRes;
    }

    public void deleteItemFromCart(Long userId, Long cartId) throws Exception {
        Optional<Cart> result = cartRepository.findById(cartId);
        if (result.isPresent()) {
            Cart cart = result.get();
            if (cart.getUser().getId().equals(userId)) {
                cartRepository.delete(cart);
            } else {
                throw new Exception("본인아님");
            }
        }
    }

    public void deleteListFromCart(Long userId, CartDeleteListReq req) {
        for (Long cartId : req.getCartIdList()) {
            cartRepository.findById(cartId).ifPresentOrElse(cart -> {
                if (cart.getUser().getId().equals(userId)) {
                    cartRepository.delete(cart);
                } else {
                    throw new RuntimeException("본인아님");
                }
            }, () -> {
                throw new RuntimeException("장바구니 항목이 존재 안함");
            });
        }
    }
}
