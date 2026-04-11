package com.darcode.curalink.config;

import com.darcode.curalink.enums.AppointmetStatus;
import com.darcode.curalink.enums.Role;
import com.darcode.curalink.model.Appointment;
import com.darcode.curalink.model.DoctorProfile;
import com.darcode.curalink.model.TimeSlot;
import com.darcode.curalink.model.User;
import com.darcode.curalink.repository.AppointmentRepository;
import com.darcode.curalink.repository.DoctorProfileRepository;
import com.darcode.curalink.repository.TimeSlotRepository;
import com.darcode.curalink.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

//@Component
@RequiredArgsConstructor
@Slf4j
@Profile("dev") // s'exécute en profil dev
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final AppointmentRepository appointmentRepository;
    private final PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker(new Locale("fr"));

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Base de données déjà peuplée, seed ignoré");
            return; // évite de re-seeder à chaque redémarrage
        }

        log.info("Démarrage du seed...");
        seedUsers();
        seedDoctors();
        seedTimeSlots();
        seedAppointments();
        log.info("Seed terminé avec succès");
    }

    private void seedUsers() {
        // Admin
        User admin = new User();
        admin.setFirstName("Admin");
        admin.setEmail("admin@curalink.com");
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);

        // Patients
        for (int i = 0; i < 20; i++) {
            User patient = new User();
            patient.setFirstName(faker.name().firstName());
            patient.setEmail(faker.internet().emailAddress());
            patient.setPassword(passwordEncoder.encode("password"));
            patient.setRole(Role.PATIENT);
            userRepository.save(patient);
        }

        log.info("Users seedés : 1 admin + 20 patients");
    }

    private void seedDoctors() {
        String[] specialties = {"Cardiologie", "Neurologie", "Pédiatrie", "Dermatologie", "Chirurgie"};

        for (String specialty : specialties) {
            User doctor = new User();
            doctor.setFirstName(faker.name().firstName());
            doctor.setEmail(faker.internet().emailAddress());
            doctor.setPassword(passwordEncoder.encode("password"));
            doctor.setRole(Role.DOCTOR);
            userRepository.save(doctor);

            DoctorProfile profile = new DoctorProfile();
            profile.setSpeciality(specialty);
            profile.setBio(faker.lorem().sentence(10));
            profile.setConsultationFee(BigDecimal.valueOf(faker.number().numberBetween(30, 150)));
            profile.setDoctor(doctor);
            doctorProfileRepository.save(profile);
        }

        log.info("Doctors seedés : 5 médecins avec profils");
    }

    private void seedTimeSlots() {
        List<User> doctors = userRepository.findByRole(Role.DOCTOR);

        doctors.forEach(doctor -> {
            for (int day = 1; day <= 5; day++) {
                for (int hour = 9; hour <= 16; hour++) {
                    TimeSlot slot = new TimeSlot();
                    slot.setDoctor(doctor);
                    slot.setStartTime(LocalDateTime.now().plusDays(day).withHour(hour).withMinute(0));
                    slot.setEndTime(LocalDateTime.now().plusDays(day).withHour(hour).withMinute(30));
                    slot.setIsAvailable(true);
                    timeSlotRepository.save(slot);
                }
            }
        });

        log.info("TimeSlots seedés : {} créneaux", timeSlotRepository.count());
    }

    private void seedAppointments() {
        List<User> patients = userRepository.findByRole(Role.PATIENT);
        List<TimeSlot> slots = timeSlotRepository.findByIsAvailable(true);

        int count = Math.min(10, slots.size());

        for (int i = 0; i < count; i++) {
            TimeSlot slot = slots.get(i);
            User patient = patients.get(faker.number().numberBetween(0, patients.size()));

            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setDoctor(slot.getDoctor());
            appointment.setTimeSlot(slot);
            appointment.setSheduledAt(slot.getStartTime());
            appointment.setReason(faker.lorem().sentence(5));
            appointment.setStatus(AppointmetStatus.CONFIRMED);
            appointmentRepository.save(appointment);

            slot.setIsAvailable(false);
            timeSlotRepository.save(slot);
        }

        log.info("Appointments seedés : {} rendez-vous", count);
    }
}