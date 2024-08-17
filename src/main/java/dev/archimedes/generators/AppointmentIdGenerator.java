package dev.archimedes.generators;

import dev.archimedes.repositories.DailyCountEntityRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class AppointmentIdGenerator implements IdentifierGenerator {

    private static final String PREFIX = "APPID";

    private final ApplicationContext applicationContext;


    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {

        var dailyCountEntityRepository = applicationContext.getBean(DailyCountEntityRepository.class);

        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateString = today.format(formatter);

        int count = dailyCountEntityRepository.getReferenceById(1).getAppointmentCount();
        dailyCountEntityRepository.updateCountBy1();

        return STR."\{PREFIX}\{dateString}C\{count}";
    }
}
