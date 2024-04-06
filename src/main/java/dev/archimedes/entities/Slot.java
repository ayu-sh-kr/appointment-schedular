package dev.archimedes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.archimedes.enums.SlotStatus;
import dev.archimedes.generators.SlotIdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "slot_id_generator")
    @GenericGenerator(name = "slot_id_generator", type = SlotIdGenerator.class)
    private String slotId;

    @Enumerated(EnumType.STRING)
    private SlotStatus status;

    private LocalTime start;

    private LocalTime end;

    private LocalDate date;

    @ManyToOne()
    @JoinColumn(name = "operator_id")
    @JsonIgnore
    private Operator operator;

    @JsonIgnore
    public boolean isBookable(){
        return !date.isBefore(LocalDate.now()) && !start.isBefore(LocalTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Slot slot = (Slot) o;

        return slotId.equals(slot.slotId);
    }

}
