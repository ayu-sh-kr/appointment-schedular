package dev.archimedes.generators;

import dev.archimedes.entities.Slot;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class SlotIdGenerator implements IdentifierGenerator {

    private static final String PREFIX = "SID";
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {

        var entity = (Slot) object;
        String start = String.valueOf(entity.getStart().getHour());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = entity.getDate().format(formatter);
        return STR."\{PREFIX}\{entity.getOperator().getUic()}\{date}S\{start}";
    }
}
