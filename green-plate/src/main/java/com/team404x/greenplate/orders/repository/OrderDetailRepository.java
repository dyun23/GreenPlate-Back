package com.team404x.greenplate.orders.repository;

import com.team404x.greenplate.orders.model.entity.OrderDetail;
import com.team404x.greenplate.orders.model.entity.Orders;
import com.team404x.greenplate.user.model.entity.User;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrdersId(Long ordersId);
}
