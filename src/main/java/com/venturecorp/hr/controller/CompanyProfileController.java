package com.venturecorp.hr.controller;

import com.venturecorp.hr.model.CompanyProfile;
import com.venturecorp.hr.repository.CompanyProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company-profiles")
@PreAuthorize("hasAnyRole('SUPERADMIN','PREMIUMADMIN')")
public class CompanyProfileController {

    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    // ✅ CREATE company profile
    @PostMapping
    public CompanyProfile create(@RequestBody CompanyProfile company) {

        if (companyProfileRepository.existsByCompanyCode(company.getCompanyCode())) {
            throw new RuntimeException("Company code already exists");
        }

        if (companyProfileRepository.existsByName(company.getName())) {
            throw new RuntimeException("Company name already exists");
        }

        return companyProfileRepository.save(company);
    }

    // ✅ GET all companies
    @GetMapping
    public List<CompanyProfile> getAll() {
        return companyProfileRepository.findAll();
    }

    // ✅ GET by ID
    @GetMapping("/{id}")
    public CompanyProfile getById(@PathVariable Long id) {
        return companyProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    // ✅ UPDATE company
    @PutMapping("/{id}")
    public CompanyProfile update(@PathVariable Long id,
                                 @RequestBody CompanyProfile updated) {

        CompanyProfile company = companyProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setName(updated.getName());
        company.setPhoneNumber(updated.getPhoneNumber());
        company.setEmail(updated.getEmail());
        company.setRemark(updated.getRemark());
        company.setLogoLink(updated.getLogoLink());
        company.setLocation(updated.getLocation());
        company.setAddress(updated.getAddress());
        company.setPrefix(updated.getPrefix());
        company.setCompanyCode(updated.getCompanyCode());
        company.setLatitude(updated.getLatitude());
        company.setLongitude(updated.getLongitude());
        company.setTimezoneString(updated.getTimezoneString());
        company.setCurrenzyString(updated.getCurrenzyString());

        return companyProfileRepository.save(company);
    }

    // ✅ DELETE company
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        if (!companyProfileRepository.existsById(id)) {
            throw new RuntimeException("Company not found");
        }

        companyProfileRepository.deleteById(id);
    }
}
