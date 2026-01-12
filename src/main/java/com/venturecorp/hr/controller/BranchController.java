package com.venturecorp.hr.controller;

import com.venturecorp.hr.model.Branch;
import com.venturecorp.hr.model.CompanyProfile;
import com.venturecorp.hr.repository.BranchRepository;
import com.venturecorp.hr.repository.CompanyProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@PreAuthorize("hasAnyRole('SUPERADMIN','PREMIUMADMIN')")
public class BranchController {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    // ✅ CREATE branch
    @PostMapping
    public Branch create(@RequestParam Long companyId,
                         @RequestBody Branch branch) {

        if (branchRepository.existsByBranchCode(branch.getBranchCode())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Branch code already exists"
            );
        }

        CompanyProfile company = companyProfileRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Company not found"
                ));

        branch.setCompany(company);

        return branchRepository.save(branch);
    }

    // ✅ GET all branches
    @GetMapping
    public List<Branch> getAll() {
        return branchRepository.findAll();
    }

    // ✅ GET branch by ID
    @GetMapping("/{id}")
    public Branch getById(@PathVariable Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Branch not found"
                ));
    }

    // ✅ UPDATE branch
    @PutMapping("/{id}")
    public Branch update(@PathVariable Long id,
                         @RequestBody Branch updated) {

        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Branch not found"
                ));

        branch.setBranchName(updated.getBranchName());
        branch.setLatitude(updated.getLatitude());
        branch.setLongitude(updated.getLongitude());
        branch.setPrefix(updated.getPrefix());
        branch.setLocation(updated.getLocation());
        branch.setAddress(updated.getAddress());
        branch.setPhoneNumber(updated.getPhoneNumber());
        branch.setRemark(updated.getRemark());
        branch.setBranchCode(updated.getBranchCode());
        branch.setEmail(updated.getEmail());
        branch.setLogoLink(updated.getLogoLink());
        branch.setManagerName(updated.getManagerName());
        branch.setTimezoneString(updated.getTimezoneString());
        branch.setCurrenzyString(updated.getCurrenzyString());

        return branchRepository.save(branch);
    }

    // ✅ DELETE branch
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        if (!branchRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Branch not found"
            );
        }

        branchRepository.deleteById(id);
    }
}
