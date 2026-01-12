package com.venturecorp.hr.repository;

import com.venturecorp.hr.model.Designation;
import com.venturecorp.hr.model.CompanyProfile;
import com.venturecorp.hr.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {

    boolean existsByDesignationNameAndBranchId(String designationName, Long branchId);

    List<Designation> findByCompanyAndBranch(CompanyProfile company, Branch branch);
}
