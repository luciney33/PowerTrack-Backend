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

    public RutinaEntity toEntity(Rutina domain) {
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

    public Rutina toDomain(RutinaEntity entity) {
        if (entity == null) return null;

        return new Rutina(
                entity.getId(),
                entity.getUsuarioId(),
                entity.getNombre(),
                entity.getDescripcion(),
                ejercicioMapper.toDomainList(entity.getEjercicios())
        );
    }
}
