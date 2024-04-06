package dev.archimedes.service;

import dev.archimedes.entities.Operator;
import dev.archimedes.entities.Slot;
import dev.archimedes.enums.SlotStatus;
import dev.archimedes.repositories.DailyCountEntityRepository;
import dev.archimedes.repositories.OperatorRepository;
import dev.archimedes.repositories.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * {@link  dev.archimedes.service.SlotPopulator}
 * is used to populate the slots for the next day.
 * It runs at midnight and populate slot for very next day.
 * @author Ayush Jaiswal */

@Service
@RequiredArgsConstructor
public class SlotPopulator {

    private final SlotRepository slotRepository;

    private final OperatorRepository operatorRepository;

    private final DailyCountEntityRepository dailyCountEntityRepository;

    /**
     * This method is scheduled to run at midnight every day.
     * It fetches all the operators and populates slots for each operator.
     * Each operator gets 24 slots, one for each hour of the day.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void populateSlots(){
        List<Operator> operators = operatorRepository.findAll();
        operators.forEach(this::populate);
    }

    @Transactional
    protected void populate(Operator operator){
        for (int i = 0; i < 24; i++){
            Slot slot = Slot.builder()
                    .date(LocalDate.now())
                    .start(LocalTime.of(i, 0))
                    .status(SlotStatus.OPEN)
                    .operator(operator)
                    .end(LocalTime.of(i, 59, 59))
                    .build();
            slotRepository.save(slot);
            operatorRepository.save(operator);
        }
    }

    /**
     * <h3>This method should run at midnight and reset the
     * count value of all the counters used for id generation
     * </h3>
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetCount(){
        dailyCountEntityRepository.resetCount();
    }
}
