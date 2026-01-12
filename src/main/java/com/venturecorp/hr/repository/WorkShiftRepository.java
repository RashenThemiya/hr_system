package com.venturecorp.hr.repository;

import com.venturecorp.hr.model.WorkShift;
import com.venturecorp.hr.model.Branch;
import com.venturecorp.hr.model.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkShiftRepository extends JpaRepository<WorkShift, Long> {

    List<WorkShift> findByCompanyAndBranch(
            CompanyProfile company,
            Branch branch
    );
}
