package com.venturecorp.hr.repository;

import com.venturecorp.hr.model.JobOpening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {

    List<JobOpening> findByStatus(JobOpening.Status status);

    List<JobOpening> findByLevel(JobOpening.Level level);

    List<JobOpening> findByStatusAndLevel(
            JobOpening.Status status,
            JobOpening.Level level
    );

    List<JobOpening> findByJobTopicContainingIgnoreCase(String keyword);

    // ✅ THIS NOW WORKS because fields exist in entity
    List<JobOpening> findByCompanyIdAndBranchId(Long companyId, Long branchId);
}
