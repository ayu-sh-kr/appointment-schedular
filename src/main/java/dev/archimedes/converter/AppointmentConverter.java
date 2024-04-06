package dev.archimedes.converter;

import dev.archimedes.dtos.AppointmentDTO;
import dev.archimedes.entities.Appointment;
import dev.archimedes.enums.AppointmentStatus;
import dev.archimedes.repositories.SlotRepository;
import dev.archimedes.utils.contracts.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentConverter implements Converter<Appointment, AppointmentDTO> {

    private final SlotRepository slotRepository;

    @Override
    public AppointmentDTO convert(Appointment appointment, AppointmentDTO appointmentDTO) {

        if(null == appointmentDTO){
            appointmentDTO = new AppointmentDTO();
        }

        appointmentDTO.setId(
                appointment.getAppointmentId()
        );

        appointmentDTO.setName(
                appointment.getName()
        );

        appointmentDTO.setNumber(
                String.valueOf(appointment.getNumber())
        );

        if(null != appointment.getStatus()){
            appointmentDTO.setStatus(
                    appointment.getStatus().name()
            );
        }

        appointmentDTO.setDate(appointment.getDate());

        appointmentDTO.setSlotId(
                String.valueOf(appointment.getSlot().getSlotId())
        );

        return appointmentDTO;
    }

    @Override
    public Appointment revert(AppointmentDTO appointmentDTO, Appointment appointment) {

        if(null == appointment){
            appointment = new Appointment();
        }

        if(null != appointmentDTO.getId()){
            appointment.setAppointmentId(appointmentDTO.getId());
        }

        appointment.setName(appointmentDTO.getName());
        appointment.setNumber(Long.valueOf(appointmentDTO.getNumber()));
        appointment.setStatus(
                AppointmentStatus.valueOf(appointmentDTO.getStatus().toUpperCase())
        );
        appointment.setDate(appointmentDTO.getDate());

        if(slotRepository.existsById(appointmentDTO.getSlotId())){
            appointment.setSlot(
                    slotRepository.getReferenceById(
                            appointmentDTO.getSlotId()
                    )
            );
        }

        return appointment;
    }
}
