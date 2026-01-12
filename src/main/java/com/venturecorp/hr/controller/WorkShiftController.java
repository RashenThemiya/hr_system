package com.venturecorp.hr.controller;

import com.venturecorp.hr.dto.WorkShiftDTO;
import com.venturecorp.hr.model.*;
import com.venturecorp.hr.repository.*;
import com.venturecorp.hr.util.TimeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/work-shifts")
@PreAuthorize("hasAnyRole('SUPERADMIN','PREMIUMADMIN')")
public class WorkShiftController {

    @Autowired
    private WorkShiftRepository workShiftRepo;

    @Autowired
    private CompanyProfileRepository companyRepo;

    @Autowired
    private BranchRepository branchRepo;

    // ✅ CREATE
    @PostMapping
    public WorkShiftDTO create(
            @RequestParam Long companyId,
            @RequestParam Long branchId,
            @RequestBody WorkShiftDTO dto
    ) {
        CompanyProfile company = companyRepo.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        Branch branch = branchRepo.findById(branchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        String tz = branch.getTimezoneString();

        OffsetDateTime startUtc = TimeUtil.branchTimeToUtc(dto.getStartTime(), tz);
        OffsetDateTime endUtc = TimeUtil.branchTimeToUtc(dto.getEndTime(), tz);

        WorkShift shift = WorkShift.builder()
                .name(dto.getName())
                .startTimeUtc(startUtc)
                .endTimeUtc(endUtc)
                .isOvernight(endUtc.isBefore(startUtc))
                .graceMinutes(dto.getGraceMinutes())
                .halfDayMinutes(dto.getHalfDayMinutes())
                .fullDayMinutes(dto.getFullDayMinutes())
                .company(company)
                .branch(branch)
                .build();

        return mapToDTO(workShiftRepo.save(shift));
    }

    // ✅ GET ALL BY COMPANY + BRANCH
    @GetMapping
    public List<WorkShiftDTO> getByCompanyBranch(
            @RequestParam Long companyId,
            @RequestParam Long branchId
    ) {
        CompanyProfile company = companyRepo.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        Branch branch = branchRepo.findById(branchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        return workShiftRepo.findByCompanyAndBranch(company, branch)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public WorkShiftDTO getById(@PathVariable Long id) {
        WorkShift shift = workShiftRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WorkShift not found"));
        return mapToDTO(shift);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public WorkShiftDTO update(
            @PathVariable Long id,
            @RequestParam Long companyId,
            @RequestParam Long branchId,
            @RequestBody WorkShiftDTO dto
    ) {
        WorkShift shift = workShiftRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WorkShift not found"));

        CompanyProfile company = companyRepo.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        Branch branch = branchRepo.findById(branchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        String tz = branch.getTimezoneString();
        OffsetDateTime startUtc = TimeUtil.branchTimeToUtc(dto.getStartTime(), tz);
        OffsetDateTime endUtc = TimeUtil.branchTimeToUtc(dto.getEndTime(), tz);

        shift.setName(dto.getName());
        shift.setStartTimeUtc(startUtc);
        shift.setEndTimeUtc(endUtc);
        shift.setIsOvernight(endUtc.isBefore(startUtc));
        shift.setGraceMinutes(dto.getGraceMinutes());
        shift.setHalfDayMinutes(dto.getHalfDayMinutes());
        shift.setFullDayMinutes(dto.getFullDayMinutes());
        shift.setCompany(company);
        shift.setBranch(branch);

        return mapToDTO(workShiftRepo.save(shift));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!workShiftRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WorkShift not found");
        }
        workShiftRepo.deleteById(id);
    }

    // 🔁 ENTITY → DTO (UTC → Branch TZ)
    private WorkShiftDTO mapToDTO(WorkShift shift) {
        String tz = shift.getBranch().getTimezoneString();

        return WorkShiftDTO.builder()
                .shiftId(shift.getShiftId())
                .name(shift.getName())
                .startTime(TimeUtil.utcToBranchTime(shift.getStartTimeUtc(), tz))
                .endTime(TimeUtil.utcToBranchTime(shift.getEndTimeUtc(), tz))
                .graceMinutes(shift.getGraceMinutes())
                .halfDayMinutes(shift.getHalfDayMinutes())
                .fullDayMinutes(shift.getFullDayMinutes())
                .companyId(shift.getCompany().getId())
                .branchId(shift.getBranch().getId())
                .timezone(tz)
                .build();
    }
}
