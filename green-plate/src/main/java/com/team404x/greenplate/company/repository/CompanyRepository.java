package com.team404x.greenplate.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team404x.greenplate.company.model.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	Company findCompanyByEmail(String email);
}
