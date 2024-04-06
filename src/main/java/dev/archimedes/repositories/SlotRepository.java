package dev.archimedes.repositories;

import dev.archimedes.entities.Slot;
import dev.archimedes.enums.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, String> {

    @Query("select count(s) >= 24 from Slot s where s.date = ?1")
    boolean existsByLocalDateTime(LocalDate date);

    @Query("select count(s) > 0 from Slot s where s.date = ?1 and s.operator.operatorId = ?2")
    boolean existsByDateAndOperator_OperatorId(LocalDate date, String operatorId);

    @Query("select count(s) = 1  from Slot s where s.date = ?1 and s.start = ?2 and s.status = 'BOOKED' and s.operator.operatorId = ?3")
    boolean isSlotBooked(LocalDate date, LocalTime start, String operatorId);

    @Query("select s from Slot s where s.date = ?1 and s.start = ?2 and s.operator.operatorId = ?3")
    Slot findSlotByDateAndStart(LocalDate date, LocalTime start, String operatorId);

    @Query("select s from Slot s where s.date = ?1 and s.operator.operatorId = ?2")
    List<Slot> findSlotByDateAndOperator_OperatorId(LocalDate date, String operatorId);

    @Query("select s from Slot s where s.date >= ?1")
    List<Slot> findAllSlotFromDate(LocalDate date);

    @Transactional
    @Modifying
    @Query("update Slot s set s.status = ?2 where s.slotId = ?1")
    void updateSlotStatusBySlotId(String slotId, SlotStatus status);

    @Transactional
    @Modifying
    @Query("update Slot s set s.status = ?3 where s.operator.operatorId = ?1 and s.date = ?2")
    void updateSlotStatusByDateAndOperatorId(String operatorId, LocalDate date, SlotStatus status);

}
