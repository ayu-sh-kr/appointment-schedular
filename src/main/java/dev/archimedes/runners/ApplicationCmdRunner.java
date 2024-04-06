package dev.archimedes.runners;

import dev.archimedes.converter.AppointmentConverter;
import dev.archimedes.entities.Appointment;
import dev.archimedes.entities.DailyCountEntity;
import dev.archimedes.entities.Operator;
import dev.archimedes.enums.AppointmentStatus;
import dev.archimedes.enums.SlotStatus;
import dev.archimedes.repositories.DailyCountEntityRepository;
import dev.archimedes.repositories.OperatorRepository;
import dev.archimedes.repositories.SlotRepository;
import dev.archimedes.service.SlotPopulator;
import dev.archimedes.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@Component
public class ApplicationCmdRunner implements CommandLineRunner {

    private final SlotRepository slotRepository;

    private final AppointmentService appointmentService;

    private final SlotPopulator slotPopulator;

    private final AppointmentConverter appointmentConverter;

    private final OperatorRepository operatorRepository;

    private final DailyCountEntityRepository dailyCountEntityRepository;

    @Override
    public void run(String... args) throws Exception {

        initializeCountEntity();

        Operator operator1 = Operator.builder()
                .operatorName("Operator A")
                .uic("OPIDA")
                .creationDate(LocalDate.now())
                .build();

        operator1 = operatorRepository.save(operator1);

        Operator operator2 = Operator.builder()
                .operatorName("Operator B")
                .uic("OPIDB")
                .creationDate(LocalDate.now())
                .build();

        operator2 = operatorRepository.save(operator2);

        Operator operator3 = Operator.builder()
                .operatorName("Operator C")
                .uic("OPIDC")
                .creationDate(LocalDate.now())
                .build();

        operator3 = operatorRepository.save(operator3);


        slotPopulator.populateSlots();

        var booked = slotRepository.isSlotBooked(LocalDate.now(), LocalTime.of(20, 0), operator1.getOperatorId());

        if (!booked){
            var slot = slotRepository.findSlotByDateAndStart(LocalDate.now(), LocalTime.of(20, 0), operator1.getOperatorId());
            slot.setStatus(SlotStatus.CLOSE);
            var appointment = Appointment.builder()
                    .name("Ayush Jaiswal")
                    .number(Long.parseLong("8931007054"))
                    .slot(slot)
                    .status(AppointmentStatus.BOOKED)
                    .date(LocalDate.now())
                    .build();
            appointmentService.book(
                    appointmentConverter.convert(appointment, null)
            );
        }

    }

    private void initializeCountEntity(){
        var dailyCountEntity = new DailyCountEntity();
        dailyCountEntity.setAppointmentCount(1);
        dailyCountEntity.setOperatorCount(1);
        dailyCountEntity.setId(1);

        dailyCountEntityRepository.save(dailyCountEntity);
    }

}
