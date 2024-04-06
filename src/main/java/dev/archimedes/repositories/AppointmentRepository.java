package dev.archimedes.repositories;

import dev.archimedes.entities.Appointment;
import dev.archimedes.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    @Transactional
    @Modifying
    @Query("update Appointment a set a.status = ?2 where a.appointmentId = ?1")
    void updateAppointmentStatusAndGetSlotId(String appointmentId, AppointmentStatus appointmentStatus);

    @Query("select a.slot.slotId from Appointment a where a.appointmentId = ?1")
    String getSlotIdByAppointmentId(String appointmentId);

    @Query("select a from Appointment a where a.date = ?1")
    List<Appointment> getAppointmentsByDate(LocalDate date);

    @Modifying
    @Transactional
    @Query("delete Appointment a where a.slot.slotId = ?1")
    void deleteAppointmentBySlot_SlotId(String slotId);

    @Query("select a from Appointment a where a.slot.operator.operatorId = ?1")
    List<Appointment> getAppointmentsBySlot_Operator_OperatorId(String operatorId);
}
