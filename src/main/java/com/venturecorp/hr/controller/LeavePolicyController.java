package com.venturecorp.hr.controller;

import com.venturecorp.hr.dto.LeavePolicyDTO;
import com.venturecorp.hr.model.LeavePolicy;
import com.venturecorp.hr.model.CompanyProfile;
import com.venturecorp.hr.model.Branch;
import com.venturecorp.hr.repository.LeavePolicyRepository;
import com.venturecorp.hr.repository.CompanyProfileRepository;
import com.venturecorp.hr.repository.BranchRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leave-policies")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERADMIN','PREMIUMADMIN')")
public class LeavePolicyController {

    private final LeavePolicyRepository leavePolicyRepository;
    private final CompanyProfileRepository companyRepository;
    private final BranchRepository branchRepository;

    // ================= CREATE =================
    @PostMapping
    public LeavePolicyDTO create(@RequestParam Long companyId,
                                 @RequestParam Long branchId,
                                 @RequestBody LeavePolicyDTO dto) {

        CompanyProfile company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        if (leavePolicyRepository.existsByCompanyIdAndBranchIdAndName(companyId, branchId, dto.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Leave policy already exists for this branch");
        }

        LeavePolicy entity = new LeavePolicy();
        entity.setCompanyId(companyId);
        entity.setBranchId(branchId);
        entity.setName(dto.getName());
        entity.setLeaveType(dto.getLeaveType());
        entity.setMaxPerMonth(dto.getMaxPerMonth());
        entity.setMaxPerYear(dto.getMaxPerYear());
        entity.setMedicalRequired(dto.getMedicalRequired());
        entity.setAllowHalfDay(dto.getAllowHalfDay());
        entity.setAllowShortLeave(dto.getAllowShortLeave());
        entity.setCarryForward(dto.getCarryForward());
        entity.setCreatedAt(LocalDateTime.now());

        LeavePolicy saved = leavePolicyRepository.save(entity);

        dto.setLeavePolicyId(saved.getLeavePolicyId());
        dto.setCompanyId(companyId);
        dto.setBranchId(branchId);

        return dto;
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public LeavePolicyDTO update(@PathVariable Long id,
                                 @RequestBody LeavePolicyDTO dto) {

        LeavePolicy existing = leavePolicyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave policy not found"));

        existing.setName(dto.getName());
        existing.setLeaveType(dto.getLeaveType());
        existing.setMaxPerMonth(dto.getMaxPerMonth());
        existing.setMaxPerYear(dto.getMaxPerYear());
        existing.setMedicalRequired(dto.getMedicalRequired());
        existing.setAllowHalfDay(dto.getAllowHalfDay());
        existing.setAllowShortLeave(dto.getAllowShortLeave());
        existing.setCarryForward(dto.getCarryForward());

        LeavePolicy updated = leavePolicyRepository.save(existing);

        dto.setLeavePolicyId(updated.getLeavePolicyId());
        dto.setCompanyId(updated.getCompanyId());
        dto.setBranchId(updated.getBranchId());

        return dto;
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public LeavePolicyDTO getById(@PathVariable Long id) {

        LeavePolicy entity = leavePolicyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave policy not found"));

        return mapToDTO(entity);
    }

    // ================= GET BY COMPANY =================
    @GetMapping("/company/{companyId}")
    public List<LeavePolicyDTO> getByCompany(@PathVariable Long companyId) {

        if (!companyRepository.existsById(companyId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found");
        }

        return leavePolicyRepository.findByCompanyId(companyId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ================= GET BY COMPANY + BRANCH =================
    @GetMapping("/company/{companyId}/branch/{branchId}")
    public List<LeavePolicyDTO> getByBranch(@PathVariable Long companyId,
                                            @PathVariable Long branchId) {

        if (!companyRepository.existsById(companyId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found");
        }

        if (!branchRepository.existsById(branchId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found");
        }

        return leavePolicyRepository.findByCompanyIdAndBranchId(companyId, branchId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        if (!leavePolicyRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Leave policy not found");
        }

        leavePolicyRepository.deleteById(id);
    }

    // ================= Helper Method =================
    private LeavePolicyDTO mapToDTO(LeavePolicy entity) {
        LeavePolicyDTO dto = new LeavePolicyDTO();
        dto.setLeavePolicyId(entity.getLeavePolicyId());
        dto.setCompanyId(entity.getCompanyId());
        dto.setBranchId(entity.getBranchId());
        dto.setName(entity.getName());
        dto.setLeaveType(entity.getLeaveType());
        dto.setMaxPerMonth(entity.getMaxPerMonth());
        dto.setMaxPerYear(entity.getMaxPerYear());
        dto.setMedicalRequired(entity.getMedicalRequired());
        dto.setAllowHalfDay(entity.getAllowHalfDay());
        dto.setAllowShortLeave(entity.getAllowShortLeave());
        dto.setCarryForward(entity.getCarryForward());
        return dto;
    }
}
