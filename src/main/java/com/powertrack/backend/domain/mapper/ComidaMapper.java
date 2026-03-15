package com.powertrack.backend.domain.mapper;

import com.powertrack.backend.data.entity.ComidaEntity;
import com.powertrack.backend.domain.model.Comida;
import org.springframework.stereotype.Component;

@Component
public class ComidaMapper {

    public Comida toDomain(ComidaEntity entity) {
        if (entity == null) return null;
        return new Comida(
                entity.getId(),
                entity.getNombre(),
                entity.getCalorias(),
                entity.getProteinas(),
                entity.getCarbohidratos(),
                entity.getGrasas(),
                entity.getCategoria()
        );
    }
}
