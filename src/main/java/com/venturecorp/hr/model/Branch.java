package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "branch")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyProfile company;

    private String branchName;
    private Double latitude;
    private Double longitude;
    private String prefix;
    private String location;
    private String address;
    private String phoneNumber;
    private String remark;
    private String branchCode;
    private String email;
    private String logoLink;
    private String managerName;
    private String timezoneString;
    private String currenzyString;
}
