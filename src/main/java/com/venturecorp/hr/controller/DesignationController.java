package com.venturecorp.hr.controller;

import com.venturecorp.hr.dto.DesignationDTO;
import com.venturecorp.hr.model.Designation;
import com.venturecorp.hr.model.CompanyProfile;
import com.venturecorp.hr.model.Branch;
import com.venturecorp.hr.repository.DesignationRepository;
import com.venturecorp.hr.repository.CompanyProfileRepository;
import com.venturecorp.hr.repository.BranchRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/designations")
@PreAuthorize("hasAnyRole('SUPERADMIN','PREMIUMADMIN')")
public class DesignationController {

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private CompanyProfileRepository companyRepository;

    @Autowired
    private BranchRepository branchRepository;

    // ✅ CREATE Designation
    @PostMapping
    public DesignationDTO create(@RequestParam Long companyId,
                                 @RequestParam Long branchId,
                                 @RequestBody Designation designation) {

        CompanyProfile company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        if (designationRepository.existsByDesignationNameAndBranchId(designation.getDesignationName(), branchId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Designation already exists in this branch");
        }

        designation.setCompany(company);
        designation.setBranch(branch);

        Designation saved = designationRepository.save(designation);
        return mapToDTO(saved);
    }

    // ✅ GET All Designations
    @GetMapping
    public List<DesignationDTO> getAll() {
        return designationRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ GET Designation by ID
    @GetMapping("/{id}")
    public DesignationDTO getById(@PathVariable Long id) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Designation not found"));
        return mapToDTO(designation);
    }

    // ✅ UPDATE Designation
    @PutMapping("/{id}")
    public DesignationDTO update(@PathVariable Long id,
                                 @RequestBody Designation updated) {

        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Designation not found"));

        designation.setDesignationName(updated.getDesignationName());
        designation.setHierarchyLevel(updated.getHierarchyLevel());

        Designation saved = designationRepository.save(designation);
        return mapToDTO(saved);
    }

    // ✅ DELETE Designation
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!designationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Designation not found");
        }
        designationRepository.deleteById(id);
    }

    // ✅ GET Designations by Company and Branch
    @GetMapping("/by-company-branch")
    public List<DesignationDTO> getByCompanyAndBranch(@RequestParam Long companyId,
                                                      @RequestParam Long branchId) {

        CompanyProfile company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        List<Designation> list = designationRepository.findByCompanyAndBranch(company, branch);

        return list.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // 🔹 Helper: map Designation -> DesignationDTO
    private DesignationDTO mapToDTO(Designation d) {
        return DesignationDTO.builder()
                .designationId(d.getDesignationId())
                .designationName(d.getDesignationName())
                .hierarchyLevel(d.getHierarchyLevel())
                .companyId(d.getCompany().getId())
                .companyName(d.getCompany().getName())
                .branchId(d.getBranch().getId())
                .branchName(d.getBranch().getBranchName())
                .build();
    }
}
