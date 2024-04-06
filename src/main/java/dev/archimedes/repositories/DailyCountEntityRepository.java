package dev.archimedes.repositories;

import dev.archimedes.entities.DailyCountEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DailyCountEntityRepository extends JpaRepository<DailyCountEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update DailyCountEntity as d set d.appointmentCount = d.appointmentCount + 1 where d.id = 1")
    void updateCountBy1();

    @Modifying
    @Transactional
    @Query("update DailyCountEntity as d set d.appointmentCount = 0 where d.id = 1")
    void resetCount();

}
