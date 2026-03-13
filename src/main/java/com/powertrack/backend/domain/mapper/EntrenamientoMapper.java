package com.powertrack.backend.domain.mapper;

import com.powertrack.backend.data.entity.RutinaEntity;
import com.powertrack.backend

.domain.model.Entrenamiento;
import org.springframework.stereotype.Component;

@Component
public class EntrenamientoMapper {

    private final EjercicioMapper ejercicioMapper;

    public EntrenamientoMapper(EjercicioMapper ejercicioMapper) {
        this.ejercicioMapper = ejercicioMapper;
    }

    public RutinaEntity toEntity(Entrenamiento domain) {
        if (domain == null) return null;
        RutinaEntity entity = new RutinaEntity();
        if (domain.id() != null && domain.id() != 0) {
            entity.setId(domain.id());
        }
        entity.setUsuarioId(domain.usuarioId());
        entity.setNombre(domain.nombre());
        entity.setDescripcion(domain.descripcion());
        return entity;
    }

    public Entrenamiento toDomain(RutinaEntity entity) {
        if (entity == null) return null;

        return new Entrenamiento(
                entity.getId(),
                entity.getUsuarioId(),
                entity.getNombre(),
                entity.getDescripcion(),
                ejercicioMapper.toDomainList(entity.getEjercicios())
        );
    }
}
