package com.powertrack.backend.data;

import com.powertrack.backend.data.entity.PlanNutricionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PlanNutricionalRepository extends JpaRepository<PlanNutricionalEntity, Long> {
    Optional<PlanNutricionalEntity> findByTipo(Integer tipo);
}
