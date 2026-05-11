package com.powertrack.backend.domain.mapper;

import com.powertrack.backend.data.entity.RutinaEntity;
import com.powertrack.backend.domain.model.Rutina;
import com.powertrack.backend.domain.model.RutinaEjercicio;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RutinaMapper {

    public Rutina toDomain(RutinaEntity entity) {
        if (entity == null) return null;
        List<RutinaEjercicio> ejercicios = entity.getRutinaEjercicios().stream()
                .map(re -> new RutinaEjercicio(
                        re.getEjercicio().getId(),
                        re.getEjercicio().getNombre(),
                        re.getEjercicio().getTipoEntrenamiento(),
                        re.getEjercicio().getImagenUrl(),
                        re.getEjercicio().getDescripcion(),
                        re.getSeries(),
                        re.getRepeticiones(),
                        re.getDescansoSeg()
                ))
                .collect(Collectors.toList());
        return new Rutina(entity.getId(), entity.getNombre(), entity.getDescripcion(),
                entity.getTipo(), ejercicios);
    }
}