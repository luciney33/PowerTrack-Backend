package com.powertrack.backend.data;

import com.powertrack.backend.data.entity.ComidaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComidaRepository extends JpaRepository<ComidaEntity, Long> {
    List<ComidaEntity> findByCategoria(String categoria);

}
