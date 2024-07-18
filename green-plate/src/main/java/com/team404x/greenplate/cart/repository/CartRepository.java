package com.team404x.greenplate.cart.repository;

import com.team404x.greenplate.cart.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}