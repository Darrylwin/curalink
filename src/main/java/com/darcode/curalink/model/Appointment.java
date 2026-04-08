package com.darcode.curalink.model;

import com.darcode.curalink.enums.AppointmetStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime sheduledAt;

    @Column(nullable = false)
    private AppointmetStatus status = AppointmetStatus.PENDING;

    private String reason;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @OneToOne
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot;

    @OneToOne(mappedBy = "appointment")
    private MedicalRecord medicalRecord;
}
