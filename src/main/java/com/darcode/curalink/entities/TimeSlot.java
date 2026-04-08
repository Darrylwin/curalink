package com.darcode.curalink.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "time_slots")
@Getter
@Setter
@NoArgsConstructor
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Column(nullable = false)
    private Boolean isAvailable = false;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @OneToOne(mappedBy = "timeSlot")
    private Appointment appointment;
}
