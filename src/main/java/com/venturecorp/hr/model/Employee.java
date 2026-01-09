package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    public enum EmploymentType {
        FULL_TIME, PART_TIME, CONTRACT, PROBATION, INTERN, TRAINEE, PERMANENT
    }

    public enum EmploymentStatus {
        PENDING, ACTIVE, INACTIVE, LEAVE
    }

    public enum EmployeeType {
        MANAGER, EMPLOYEE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String customEmployeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nameWithInitials;
    private String fullName;
    private String preferredName;
    private String nic;
    private String passport;
    private LocalDate dateOfBirth;
    private String gender;
    private String maritalStatus;
    private String nationality;
    private String religion;
    private String race;
    private String permanentAddress;
    private String stateProvince;
    private String zipCode;
    private String currentAddress;
    private String personalMobileNumber;
    private String personalEmail;
    private LocalDate dateOfHire;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    private Long designationId;
    private Long departmentId;
    private String jobCategory;
    private Long workingShiftId;
    private String workLocation;
    private LocalDate startDate;
    private LocalDate confirmationDate;
    private LocalDate resignationDate;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;

    @Enumerated(EnumType.STRING)
    private EmployeeType type;

    private Boolean resident;
    private Double latitude;
    private Double longitude;
}
