package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bank_names")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankNames {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankId;

    private String bankName;
    private String bankCode;
}
