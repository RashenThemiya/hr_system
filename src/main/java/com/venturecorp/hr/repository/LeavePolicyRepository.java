package com.venturecorp.hr.repository;

import com.venturecorp.hr.model.LeavePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeavePolicyRepository extends JpaRepository<LeavePolicy, Long> {

    List<LeavePolicy> findByCompanyId(Long companyId);

    List<LeavePolicy> findByCompanyIdAndBranchId(Long companyId, Long branchId);

    boolean existsByCompanyIdAndBranchIdAndName(
            Long companyId,
            Long branchId,
            String name
    );
}
