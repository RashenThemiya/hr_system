package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_theme")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyTheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the company this theme belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyProfile company;

    // Theme key, e.g., primaryColor, secondaryColor, navbarColor
    @Column(nullable = false)
    private String key;

    // Value of the theme key, e.g., #0055FF
    @Column(nullable = false)
    private String value;
}
