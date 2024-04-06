package dev.archimedes.utils.validators;

import dev.archimedes.enums.AppointmentStatus;
import dev.archimedes.utils.validators.annotations.ValidAppointmentStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * {@link AppointmentStatusValidator}
 * It is used to validate the from field for <strong>checking</strong> whether requested
 * {@link AppointmentStatus} is valid or not
 */
@Slf4j
public class AppointmentStatusValidator implements ConstraintValidator<ValidAppointmentStatus, String> {

    @Override
    public void initialize(ValidAppointmentStatus constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String status, ConstraintValidatorContext constraintValidatorContext) {
        log.info(STR."Validating status: \{status}");
        for (AppointmentStatus appointmentStatus: AppointmentStatus.values()){
            if(appointmentStatus.name().equals(status.toUpperCase())) return true;
        }
        return false;
    }
}