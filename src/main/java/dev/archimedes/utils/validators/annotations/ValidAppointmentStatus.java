package dev.archimedes.utils.validators.annotations;

import dev.archimedes.utils.validators.AppointmentStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AppointmentStatusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAppointmentStatus {
    String message() default "Invalid Appointment Status";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
