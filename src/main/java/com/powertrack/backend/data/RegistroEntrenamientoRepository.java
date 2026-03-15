package com.powertrack.backend.data;

import com.powertrack.backend.data.entity.RegistroEntrenamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroEntrenamientoRepository extends JpaRepository<RegistroEntrenamientoEntity, Long> {
    List<RegistroEntrenamientoEntity> findByUsuarioUsername(String username);
}
