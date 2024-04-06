package dev.archimedes.service;

import dev.archimedes.entities.Slot;
import dev.archimedes.enums.SlotStatus;
import dev.archimedes.repositories.OperatorRepository;
import dev.archimedes.repositories.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link OperatorService} is used to perform following actions
 * {@summary
 *  <div><strong>Fetch Slot</strong></div>
 *  <div><strong>Fetch Slot By Date</strong></div>
 *  <div><strong>Close Slot</strong></div>
 *  <div><strong>Open Slot</strong></div>
 * }
 */
@Service
@RequiredArgsConstructor
public class OperatorService {

    private final OperatorRepository operatorRepository;
    private final SlotRepository slotRepository;


    public ResponseEntity<?> getSlotsByDate(String operatorId, LocalDate date) {
        List<Slot> slots = slotRepository.findSlotByDateAndOperator_OperatorId(date, operatorId);
        return new ResponseEntity<>(slots, HttpStatus.OK);
    }

    public ResponseEntity<?> getSlotsFromDate(LocalDate date) {
        return new ResponseEntity<>(slotRepository.findAllSlotFromDate(date), HttpStatus.OK);
    }

    public ResponseEntity<?> closeSlot(String slotId) {
        if (slotRepository.existsById(slotId)) {
            slotRepository.updateSlotStatusBySlotId(slotId, SlotStatus.CLOSE);
            return new ResponseEntity<>("Slot closed successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Slot does not exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> closeSlot(String operatorId, LocalDate date) {
        if (slotRepository.existsByDateAndOperator_OperatorId(date, operatorId)) {
            slotRepository.updateSlotStatusByDateAndOperatorId(operatorId, date, SlotStatus.CLOSE);
            return new ResponseEntity<>(STR."All slot closed for date: \{date}", HttpStatus.OK);
        }
        return new ResponseEntity<>(STR."No slot found for date: \{date}", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> openSlot(String slotId) {
        if (slotRepository.existsById(slotId)){
            slotRepository.updateSlotStatusBySlotId(slotId, SlotStatus.OPEN);
            return new ResponseEntity<>(STR."Update the slot status to OPEN for slot: \{slotId}", HttpStatus.OK);
        }
        return new ResponseEntity<>(STR."No such slot with slotId: \{slotId}", HttpStatus.BAD_REQUEST);
    }
}
