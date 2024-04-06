package dev.archimedes.entities;

import dev.archimedes.generators.OperatorIdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operatorId_generator")
    @GenericGenerator(name = "operatorId_generator", type = OperatorIdGenerator.class)
    private String operatorId;

    private String operatorName;

    private String uic;

    private LocalDate creationDate;

    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Slot> slots = new LinkedHashSet<>();

}
