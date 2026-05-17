package com.powertrack.backend.domain.mapper;

import com.powertrack.backend.data.entity.EjercicioEntity;
import com.powertrack.backend.domain.model.Ejercicio;
import org.springframework.stereotype.Component;



@Component
public class EjercicioMapper {

    public Ejercicio toDomain(EjercicioEntity entity) {
        if (entity == null) return null;
        return new Ejercicio(
                entity.getId(),
                entity.getNombre(),
                entity.getTipoEntrenamiento(),
                entity.getImagenUrl(),
                entity.getDescripcion()
        );
    }

}

