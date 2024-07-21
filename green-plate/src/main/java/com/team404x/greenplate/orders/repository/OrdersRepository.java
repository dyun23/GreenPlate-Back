package com.team404x.greenplate.orders.repository;

import com.team404x.greenplate.orders.model.entity.Orders;
import com.team404x.greenplate.user.model.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long>  {
    List<Orders> findAllByUser(User user);

    @Query("SELECT o FROM Orders o WHERE o.user.id = :userId")
    Page<Orders> findOrdersByUserId(@Param("userId") Long userId, Pageable pageable);
}
