package com.venturecorp.hr.controller;

import com.venturecorp.hr.dto.DepartmentDTO;
import com.venturecorp.hr.model.Branch;
import com.venturecorp.hr.model.CompanyProfile;
import com.venturecorp.hr.model.Department;
import com.venturecorp.hr.repository.BranchRepository;
import com.venturecorp.hr.repository.CompanyProfileRepository;
import com.venturecorp.hr.repository.DepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/departments")
@PreAuthorize("hasAnyRole('SUPERADMIN','PREMIUMADMIN')")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CompanyProfileRepository companyRepository;

    @Autowired
    private BranchRepository branchRepository;

    // ✅ CREATE Department
    @PostMapping
    public DepartmentDTO create(@RequestParam Long companyId,
                                @RequestParam Long branchId,
                                @RequestBody Department department) {

        CompanyProfile company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        if (departmentRepository.existsByDepartmentNameAndBranchId(department.getDepartmentName(), branchId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department already exists in this branch");
        }

        department.setCompany(company);
        department.setBranch(branch);

        Department saved = departmentRepository.save(department);
        return mapToDTO(saved);
    }

    // ✅ GET All Departments
    @GetMapping
    public List<DepartmentDTO> getAll() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ GET Department by ID
    @GetMapping("/{id}")
    public DepartmentDTO getById(@PathVariable Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
        return mapToDTO(department);
    }

    // ✅ UPDATE Department
    @PutMapping("/{id}")
    public DepartmentDTO update(@PathVariable Long id,
                                @RequestBody Department updated) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));

        department.setDepartmentName(updated.getDepartmentName());
        // Optional: update manager
        department.setManager(updated.getManager());

        Department saved = departmentRepository.save(department);
        return mapToDTO(saved);
    }

    // ✅ DELETE Department
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found");
        }
        departmentRepository.deleteById(id);
    }

    // ✅ GET Departments by Company and Branch
    @GetMapping("/by-company-branch")
    public List<DepartmentDTO> getByCompanyAndBranch(@RequestParam Long companyId,
                                                     @RequestParam Long branchId) {

        CompanyProfile company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        List<Department> departments = departmentRepository.findByCompanyAndBranch(company, branch);

        return departments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Helper method to map Department -> DepartmentDTO
    private DepartmentDTO mapToDTO(Department dep) {
        return DepartmentDTO.builder()
                .departmentId(dep.getDepartmentId())
                .departmentName(dep.getDepartmentName())
                .companyName(dep.getCompany().getName())     // Must match your CompanyProfile field
                      .branchId(dep.getBranch().getId())
                    .companyId(dep.getCompany().getId())
                .branchName(dep.getBranch().getBranchName())// Must match your Branch field
                .managerId(dep.getManager() != null ? dep.getManager().getEmployeeId() : null)
                .build();
    }
}
