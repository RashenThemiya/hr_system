package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumber;
    private String email;
    private String remark;
    private String logoLink;
    private String location;
    private String address;
    private String prefix;
    private String companyCode;
    private Double latitude;
    private Double longitude;
    private String timezoneString;
    private String currenzyString;
}
