package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bank_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private BankNames bank;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String branchName;
    private String branchCode;
    private String accountNumber;
    private String accountHolderName;
    private String bankDocumentAttachment;
}
