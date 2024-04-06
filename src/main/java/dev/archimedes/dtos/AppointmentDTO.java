package dev.archimedes.dtos;

import dev.archimedes.utils.validators.annotations.ValidAppointmentStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class AppointmentDTO {

    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String number;

    @NotBlank
    @ValidAppointmentStatus
    private String status;

    @NotBlank
    private String slotId;

    @FutureOrPresent
    private LocalDate date;

}
