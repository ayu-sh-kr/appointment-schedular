package dev.archimedes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class DailyCountEntity {

    @Id
    private int id;
    private int appointmentCount;
    private int operatorCount;

}
