package dev.archimedes.entities;

import dev.archimedes.enums.AppointmentStatus;
import dev.archimedes.generators.AppointmentIdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_id_generator")
    @GenericGenerator(name = "app_id_generator", type = AppointmentIdGenerator.class)
    private String appointmentId;

    private String name;

    private Long number;

    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}
