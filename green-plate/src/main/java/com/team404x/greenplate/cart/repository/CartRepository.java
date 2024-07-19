package com.team404x.greenplate.cart.repository;

import com.team404x.greenplate.cart.model.entity.Cart;
import com.team404x.greenplate.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserId(Long userId);
}