package dev.archimedes.repositories;

import dev.archimedes.entities.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, String> {
    
}
