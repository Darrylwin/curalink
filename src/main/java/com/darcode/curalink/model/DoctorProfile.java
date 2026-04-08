package com.darcode.curalink.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "doctors_profiles")
@Getter
@Setter
@NoArgsConstructor
public class DoctorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String speciality;

    private String bio;

    @Column(nullable = false)
    private BigDecimal consultationFee;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;
}
