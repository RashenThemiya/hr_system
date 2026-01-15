package com.venturecorp.hr.controller;

import com.venturecorp.hr.dto.JobOpeningDTO;
import com.venturecorp.hr.model.JobOpening;
import com.venturecorp.hr.repository.JobOpeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/job-openings")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERADMIN','PREMIUMADMIN')")
public class JobOpeningController {

    private final JobOpeningRepository repository;
    private final S3Client s3Client;

    @Value("${R2_BUCKET}")
    private String bucket;

    @Value("${R2_PUBLIC_URL}")
    private String publicUrl;

    // ================= CREATE Job Opening =================
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<JobOpeningDTO> create(
            @RequestParam Long companyId,
            @RequestParam Long branchId,
            @RequestPart("job") JobOpeningDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            String filename = uploadFileToR2(file);
            dto.setPostImageLink(filename);
        }

        JobOpening entity = JobOpening.builder()
                .companyId(companyId)
                .branchId(branchId)
                .jobTopic(dto.getJobTopic())
                .postImageLink(dto.getPostImageLink())
                .description(dto.getDescription())
                .experience(dto.getExperience())
                .responsibility(dto.getResponsibility())
                .qualification(dto.getQualification())
                .closingDate(dto.getClosingDate())
                .status(dto.getStatus())
                .level(dto.getLevel())
                .linkedin(dto.getLinkedin())
                .topJob(dto.getTopJob())
                .link(dto.getLink())
                .build();

        JobOpening saved = repository.save(entity);
        dto.setId(saved.getId());
        return ResponseEntity.ok(dto);
    }

    // ================= UPDATE Job Opening =================
    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<JobOpeningDTO> update(
            @PathVariable Long id,
            @RequestPart("job") JobOpeningDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        return repository.findById(id).map(existing -> {
            if (file != null && !file.isEmpty()) {
                try {
                    String filename = uploadFileToR2(file);
                    existing.setPostImageLink(filename);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to upload file", e);
                }
            }

            existing.setCompanyId(dto.getId()); // optionally update companyId
            existing.setBranchId(dto.getId());  // optionally update branchId
            existing.setJobTopic(dto.getJobTopic());
            existing.setDescription(dto.getDescription());
            existing.setExperience(dto.getExperience());
            existing.setResponsibility(dto.getResponsibility());
            existing.setQualification(dto.getQualification());
            existing.setClosingDate(dto.getClosingDate());
            existing.setStatus(dto.getStatus());
            existing.setLevel(dto.getLevel());
            existing.setLinkedin(dto.getLinkedin());
            existing.setTopJob(dto.getTopJob());
            existing.setLink(dto.getLink());

            JobOpening updated = repository.save(existing);
            dto.setId(updated.getId());
            dto.setPostImageLink(updated.getPostImageLink());
            return ResponseEntity.ok(dto);
        }).orElse(ResponseEntity.notFound().build());
    }

    // ================= GET ALL =================
    @GetMapping
    public List<JobOpeningDTO> getAll() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<JobOpeningDTO> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(job -> ResponseEntity.ok(mapToDTO(job)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ================= GET by Company & Branch =================
    @GetMapping("/by-company-branch")
    public List<JobOpeningDTO> getByCompanyAndBranch(
            @RequestParam Long companyId,
            @RequestParam Long branchId) {
        return repository.findByCompanyIdAndBranchId(companyId, branchId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ================= Helper: Upload file to R2 =================
    private String uploadFileToR2(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(filename)
                        .contentType(file.getContentType())
                        .build(),
                software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes())
        );

        return publicUrl + "/" + filename;
    }
@PatchMapping("/{id}/status")
public ResponseEntity<JobOpeningDTO> updateStatus(
        @PathVariable Long id,
        @RequestParam JobOpening.Status status
) {
    return repository.findById(id).map(job -> {

        job.setStatus(status);

        JobOpening updated = repository.save(job);

        return ResponseEntity.ok(mapToDTO(updated));

    }).orElse(ResponseEntity.notFound().build());
}

    // ================= Helper: Map entity -> DTO =================
    private JobOpeningDTO mapToDTO(JobOpening job) {
        return JobOpeningDTO.builder()
                .id(job.getId())
                .jobTopic(job.getJobTopic())
                .postImageLink(job.getPostImageLink())
                .description(job.getDescription())
                .experience(job.getExperience())
                .responsibility(job.getResponsibility())
                .qualification(job.getQualification())
                .closingDate(job.getClosingDate())
                .status(job.getStatus())
                .level(job.getLevel())
                .linkedin(job.getLinkedin())
                .topJob(job.getTopJob())
                .link(job.getLink())
                .build();
    }
}
