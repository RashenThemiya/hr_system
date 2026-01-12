package com.venturecorp.hr.repository;

import com.venturecorp.hr.model.Branch;
import com.venturecorp.hr.model.CompanyProfile;
import com.venturecorp.hr.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Check if a department exists in a specific branch by name
    boolean existsByDepartmentNameAndBranchId(String departmentName, Long branchId);

    // Get all departments by company and branch
    List<Department> findByCompanyAndBranch(CompanyProfile company, Branch branch);

}
