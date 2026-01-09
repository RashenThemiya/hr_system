package com.venturecorp.hr.repository;

import com.venturecorp.hr.model.BankNames;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankNamesRepository extends JpaRepository<BankNames, Long> {
    boolean existsByBankName(String bankName);
    boolean existsByBankCode(String bankCode);
}
