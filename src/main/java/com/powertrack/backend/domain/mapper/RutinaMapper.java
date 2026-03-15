package com.powertrack.backend.domain.mapper;

import com.powertrack.backend.data.entity.RutinaEntity;
import com.powertrack.backend.domain.model.Rutina;
import org.springframework.stereotype.Component;

@Component
public class RutinaMapper {

    private final EjercicioMapper ejercicioMapper;

    public RutinaMapper(EjercicioMapper ejercicioMapper) {
        this.ejercicioMapper = ejercicioMapper;
    }

    public Rutina toDomain(RutinaEntity entity) {
        if (entity == null) return null;
        return new Rutina(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getTipo(),
                ejercicioMapper.toDomainList(entity.getEjercicios())
        );
    }
}
