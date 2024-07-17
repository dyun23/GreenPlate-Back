package com.team404x.greenplate.keyword.repository;

import com.team404x.greenplate.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
  Keyword findByName(String name);
}