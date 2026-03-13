package com.powertrack.backend.data;

import com.powertrack.backend

.data.entity.EjercicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjercicioRepository extends JpaRepository<EjercicioEntity, Long> {
    List<EjercicioEntity> findByTipoEntrenamiento(String tipoEntrenamiento);
}

