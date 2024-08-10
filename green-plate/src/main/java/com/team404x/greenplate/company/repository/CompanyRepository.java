package com.team404x.greenplate.company.repository;

import com.team404x.greenplate.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.team404x.greenplate.company.model.entity.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	Company findCompanyByEmail(String email);

	Optional<Company> findByEmail(String email);
}
