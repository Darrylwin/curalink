package com.darcode.curalink.entities;

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

    private User doctor;

    private User patient;
}
