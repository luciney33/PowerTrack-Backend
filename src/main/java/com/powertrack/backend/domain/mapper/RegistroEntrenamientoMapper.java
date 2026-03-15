package com.powertrack.backend.domain.mapper;

import com.powertrack.backend.data.entity.RegistroDetalleEntity;
import com.powertrack.backend.data.entity.RegistroEntrenamientoEntity;
import com.powertrack.backend.domain.model.RegistroDetalle;
import com.powertrack.backend.domain.model.RegistroEntrenamiento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegistroEntrenamientoMapper {

    private final EjercicioMapper ejercicioMapper;

    public RegistroEntrenamientoMapper(EjercicioMapper ejercicioMapper) {
        this.ejercicioMapper = ejercicioMapper;
    }

    public RegistroEntrenamiento toDomain(RegistroEntrenamientoEntity entity) {
        if (entity == null) return null;
        List<RegistroDetalle> detalles = entity.getDetalles().stream()
                .map(this::detalleToDomain)
                .collect(Collectors.toList());
        return new RegistroEntrenamiento(
                entity.getId(),
                entity.getUsuario().getId(),
                entity.getRutina() != null ? entity.getRutina().getId() : null,
                entity.getFecha(),
                entity.getObservaciones(),
                detalles
        );
    }

    private RegistroDetalle detalleToDomain(RegistroDetalleEntity detalle) {
        return new RegistroDetalle(
                detalle.getId(),
                detalle.getRegistro().getId(),
                ejercicioMapper.toDomain(detalle.getEjercicio()),
                detalle.getSeries(),
                detalle.getRepeticiones(),
                detalle.getPeso()
        );
    }
}