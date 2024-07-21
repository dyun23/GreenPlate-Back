package com.team404x.greenplate.user.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team404x.greenplate.user.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByEmail(String email);
	User findUserByEmailAndEnabled(String email, boolean enabled);

	@Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.id = :userId")
	User findUserWithOrders(@Param("userId") Long userId);

	@Query("SELECT u FROM User u " +
		"JOIN FETCH u.orders o " +
		"WHERE u.id = :userId AND o.id = :ordersId")
	Optional<User> findUserWithSpecificOrder(@Param("userId") Long userId, @Param("ordersId") Long ordersId);
}
