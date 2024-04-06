package dev.archimedes.generators;

import dev.archimedes.entities.Operator;
import dev.archimedes.repositories.DailyCountEntityRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class OperatorIdGenerator implements IdentifierGenerator {

    private static final String PREFIX = "OPID";

    private final ApplicationContext applicationContext;
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {

        var counterProvider = applicationContext.getBean(DailyCountEntityRepository.class);

        var operator = (Operator) object;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = operator.getCreationDate().format(formatter);
        var counter = counterProvider.getReferenceById(1);
        int operatorCount = counter.getOperatorCount();
        counter.setOperatorCount(operatorCount + 1);
        counterProvider.save(counter);
        return PREFIX + date + "C" + operatorCount;
    }
}
