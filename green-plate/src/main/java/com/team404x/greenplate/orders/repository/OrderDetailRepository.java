package com.team404x.greenplate.orders.repository;

import com.team404x.greenplate.company.entity.Company;
import com.team404x.greenplate.orders.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
