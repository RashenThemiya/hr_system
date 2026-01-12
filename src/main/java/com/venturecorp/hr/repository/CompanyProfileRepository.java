package com.venturecorp.hr.repository;

import com.venturecorp.hr.model.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long> {

    boolean existsByCompanyCode(String companyCode);
    boolean existsByName(String name);
}
