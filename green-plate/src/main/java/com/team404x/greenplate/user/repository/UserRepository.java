package com.team404x.greenplate.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team404x.greenplate.user.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByEmail(String email);
}
