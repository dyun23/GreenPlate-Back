package com.team404x.greenplate.company;

import com.team404x.greenplate.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
