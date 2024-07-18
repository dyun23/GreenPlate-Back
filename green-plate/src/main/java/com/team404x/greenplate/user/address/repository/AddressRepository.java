package com.team404x.greenplate.user.address.repository;

import com.team404x.greenplate.orders.model.entity.Orders;
import com.team404x.greenplate.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.team404x.greenplate.user.address.entity.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserAndDefultAddrTrue(User user);
}
