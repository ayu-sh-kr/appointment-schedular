package dev.archimedes.service;

import dev.archimedes.converter.AppointmentConverter;
import dev.archimedes.dtos.AppointmentDTO;
import dev.archimedes.entities.Appointment;
import dev.archimedes.entities.Slot;
import dev.archimedes.enums.AppointmentStatus;
import dev.archimedes.enums.SlotStatus;
import dev.archimedes.repositories.AppointmentRepository;
import dev.archimedes.repositories.SlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final SlotRepository slotRepository;

    private final AppointmentConverter appointmentConverter;

    /**
     * Books an appointment for a given slot.
     * <p>
     * This method will first check if the slot is bookable. If it is, it will set the status of the slot to CLOSED,
     * delete any existing appointments for the slot, and then save the new appointment. If the slot is not bookable,
     * it will return a response indicating that the slot is already booked or closed.
     *
     * @param appointmentDTO the data transfer object containing the details of the appointment to be booked
     * @return a ResponseEntity with a message indicating the result of the booking operation
     */
    @Transactional
    public ResponseEntity<?> book(AppointmentDTO appointmentDTO){

        Appointment appointment = appointmentConverter.revert(appointmentDTO, null);
        Slot slot = appointment.getSlot();

        log.info(STR."Is slot bookable: \{slot.isBookable()}");

        if (!slot.isBookable() || slot.getStatus().equals(SlotStatus.CLOSE)){
            return new ResponseEntity<>("Slot Already Booked or Closed", HttpStatus.BAD_REQUEST);
        }

        appointment.setStatus(AppointmentStatus.BOOKED);
        slot.setStatus(SlotStatus.CLOSE);
        appointment.setSlot(slot);
        slotRepository.save(slot);
        appointmentRepository.deleteAppointmentBySlot_SlotId(slot.getSlotId());
        appointment = appointmentRepository.save(appointment);

        return new ResponseEntity<>(STR."Appointment booked with Id: \{appointment.getAppointmentId()}", HttpStatus.CREATED);
    }

    /**
     * @return all the appointments from database
     */
    public ResponseEntity<?> getAppointments(){
        return new ResponseEntity<>(appointmentRepository.findAll(), HttpStatus.OK);
    }

    /**
     *
     * @param date it is the booking date for which {@link Appointment} is requested
     * @return {@link Appointment} from database where the booking date matches
     */
    public ResponseEntity<?> getAppointments(LocalDate date){
        return new ResponseEntity<>(
                appointmentRepository.getAppointmentsByDate(date).stream().
                        map(appointment -> appointmentConverter.convert(appointment, null))
                        .toList(),
                HttpStatus.OK
        );
    }

    /**
     * @param operatorId it is operator's id who booked the appointment
     * @return {@link Appointment } for the given operatorId
     */
    public ResponseEntity<?> getAppointments(String operatorId){
        return new ResponseEntity<>(
                appointmentRepository.getAppointmentsBySlot_Operator_OperatorId(operatorId)
                        .stream()
                        .map(appointment -> appointmentConverter.convert(appointment, null))
                        .toList(), HttpStatus.OK
        );
    }

    /**
     * Checks if the appointment exist for given appointmentId.
     * If appointment exist then change it's {@link AppointmentStatus} to CANCEL
     * and {@link SlotStatus} to OPEN
     * @param appointmentId it the id of the {@link Appointment} for which cancellation have been requested
     * @return response entity with message for the action happened
     */
    public ResponseEntity<?> cancel(String appointmentId){
        if(appointmentRepository.existsById(appointmentId)){
            appointmentRepository.updateAppointmentStatusAndGetSlotId(appointmentId, AppointmentStatus.CANCELLED);
            String slotId = appointmentRepository.getSlotIdByAppointmentId(appointmentId);
            slotRepository.updateSlotStatusBySlotId(slotId, SlotStatus.OPEN);
            return new ResponseEntity<>(STR."Appointment with id: \{appointmentId} Cancelled Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(STR."No appointment found with id: \{appointmentId}", HttpStatus.BAD_REQUEST);
    }

}
