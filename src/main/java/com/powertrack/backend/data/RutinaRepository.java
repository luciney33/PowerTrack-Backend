package com.powertrack.backend.data;

import com.powertrack.backend.data.entity.RutinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RutinaRepository extends JpaRepository<RutinaEntity, Long> {

    @Query("SELECT e FROM RutinaEntity e LEFT JOIN FETCH e.ejercicios")
    List<RutinaEntity> findAllWithEjercicios();

    @Query("SELECT e FROM RutinaEntity e LEFT JOIN FETCH e.ejercicios WHERE e.id = :id")
    Optional<RutinaEntity> findByIdWithEjercicios(@Param("id") Long id);
}

