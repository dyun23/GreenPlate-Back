package com.team404x.greenplate.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team404x.greenplate.user.keyword.entity.UserKeyword;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long> {
	UserKeyword findByUserIdAndKeywordId(Long userId, Long keywordId);
	void deleteByUserId(Long userId);
}
