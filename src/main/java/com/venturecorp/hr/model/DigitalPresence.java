package com.venturecorp.hr.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "digital_presence")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DigitalPresence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String presence; // facebook, instagram, linkedin
    private String url;
}
