package com.team404x.greenplate.cart.repository;

import com.team404x.greenplate.cart.model.entity.Cart;
import com.team404x.greenplate.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
//    List<Cart> findAllByUserId(Long userId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.item WHERE c.user = :user")
    List<Cart> findByUserWithItems(User user);
}