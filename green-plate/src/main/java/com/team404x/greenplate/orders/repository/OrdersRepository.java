package com.team404x.greenplate.orders.repository;

import com.team404x.greenplate.orders.model.entity.Orders;
import com.team404x.greenplate.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long>  {
    List<Orders> findAllByUser(User user);
}
