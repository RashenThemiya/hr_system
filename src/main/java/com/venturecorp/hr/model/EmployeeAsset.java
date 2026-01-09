package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee_asset")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAsset {

    public enum AssetType {
        LAPTOP, ROUTER, PHONE, CAR, BIKE, HOUSE, MONITOR, COMPUTER, OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    private String serialNumber;
    private LocalDate assignedDate;
    private LocalDate returnedDate;
}
