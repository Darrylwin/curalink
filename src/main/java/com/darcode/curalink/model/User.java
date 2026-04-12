package com.darcode.curalink.model;

import com.darcode.curalink.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.PATIENT;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "doctor")
    private DoctorProfile doctorProfile;

    @OneToMany(mappedBy = "doctor")
    @JsonBackReference
    private List<TimeSlot> timeSlots;

    @OneToMany(mappedBy = "doctor")
    @JsonBackReference
    private List<Appointment> doctorAppointments = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    @JsonBackReference
    private List<Appointment> patientAppointment = new ArrayList<>();
}

