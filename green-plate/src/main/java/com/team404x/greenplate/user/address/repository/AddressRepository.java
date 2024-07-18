package com.team404x.greenplate.user.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team404x.greenplate.user.address.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
